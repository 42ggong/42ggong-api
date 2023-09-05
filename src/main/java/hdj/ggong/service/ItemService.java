package hdj.ggong.service;

import hdj.ggong.common.enums.KeepStatus;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.User;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
import hdj.ggong.dto.item.PullOutItemsRequest;
import hdj.ggong.mapper.ItemMapper;
import hdj.ggong.repository.ItemRepository;
import hdj.ggong.repository.UserRepository;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.slack.SlackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemHistoryService itemHistoryService;
    private final UserRepository userRepository;
    private final SlackService slackService;

    public CreateItemResponse createItem(CustomUserDetails userDetails, CreateItemRequest createItemRequest) {
        User user = userDetails.getUser();
        String keepIdentifier = generateKeepIdentifier();
        while (itemRepository.existsByKeepIdentifier(keepIdentifier)) {
            keepIdentifier = generateKeepIdentifier();
        }
        Item item = itemRepository.save(itemMapper.createItemRequestToItem(user, createItemRequest, keepIdentifier));
        itemHistoryService.recordHistory(user, item);
        slackService.sendMessage(user.getSlackId(), item.getKeepIdentifier() + ": 보관했습니다.");
        return itemMapper.itemToCreateItemResponse(item);
    }

    public void pullOutItem(CustomUserDetails userDetails, PullOutItemsRequest pullOutItemsRequest) {
        AtomicInteger benefitPoint = new AtomicInteger();
        pullOutItemsRequest.getKeepIdentifierList()
                .forEach(keepIdentifier -> itemRepository.findByKeepIdentifier(keepIdentifier)
                        .map(item -> {
                            if (item.isOwned(userDetails.getId())) {
                                item.changeKeepStatusToPull();
                                slackService.sendMessage(userDetails.getSlackId(), keepIdentifier + ": 꺼냈습니다.");
                            } else if (item.isKeepExpired()) {
                                item.changeKeepStatusToDisused();
                                benefitPoint.addAndGet(1);
                                User itemOwnedUser = item.getUser();
                                itemOwnedUser.givenPenaltyPoint();
                                userRepository.save(itemOwnedUser);
                                slackService.sendMessage(itemOwnedUser.getSlackId(), keepIdentifier + ": 폐기처리 되었습니다.");
                            }
                            itemRepository.save(item);
                            itemHistoryService.recordHistory(userDetails.getUser(), item);
                            return null;
                        })
                );
        User user = userDetails.getUser();
        user.givenBenefitPoint(benefitPoint.get());
        userRepository.save(user);
    }

    /*
     * keepIdentifier를 가진 아이템이 아래의 조건이면 정보를 반환해준다.
     * - 내 아이템
     * - 보관 만료 기간이 지난 아이템
     * - 내가 꺼낸 아이템
     * - 폐기된 아이템
     * */
    public Optional<ItemInfoResponse> searchItem(CustomUserDetails userDetails, String keepIdentifier) {
        return itemRepository.findByKeepIdentifier(keepIdentifier)
                .map(item -> {
                    if (isItemAccessible(userDetails, item)) {
                        return itemMapper.itemToItemInfoResponse(userDetails, item);
                    }
                    return null;
                });
    }

    private boolean isItemAccessible(CustomUserDetails userDetails, Item item) {
        return item.isOwned(userDetails.getId())
                || item.isKeepExpired()
                || KeepStatus.PULLOUT.equals(item.getKeepStatus())
                || KeepStatus.DISUSED.equals(item.getKeepStatus());
    }

    public List<ItemInfoResponse> getMyKeepItemInfoList(CustomUserDetails userDetails) {
        return itemRepository.findAllByUserIdAndKeepStatus(userDetails.getId(), KeepStatus.KEEP).stream()
                .map(item -> itemMapper.itemToItemInfoResponse(userDetails, item))
                .collect(Collectors.toList());
    }

    public List<ItemInfoResponse> getAllFilteredItemsInfoList(CustomUserDetails userDetails, KeepStatus keepStatus, Boolean isExpired) {
        Stream<Item> itemStream = itemRepository.findAllByOrderByKeepExpiryDate().stream();
        if (keepStatus != null) {
            itemStream = itemStream.filter(item -> item.getKeepStatus() == keepStatus);
        }
        if (isExpired != null) {
            itemStream = itemStream.filter(item -> item.isKeepExpired() == isExpired);
        }
        return itemStream
                .map(item -> itemMapper.itemToItemInfoResponse(userDetails, item))
                .collect(Collectors.toList());
    }

    private String generateKeepIdentifier() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

}
