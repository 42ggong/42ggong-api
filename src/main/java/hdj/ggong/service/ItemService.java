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
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public CreateItemResponse createItem(CustomUserDetails userDetails, CreateItemRequest createItemRequest) {
        User user = userDetails.getUser();
        String keepIdentifier = generateKeepIdentifier();
        while (itemRepository.existsByKeepIdentifier(keepIdentifier)) {
            keepIdentifier = generateKeepIdentifier();
        }
        Item item = itemRepository.save(itemMapper.createItemRequestToItem(user, createItemRequest, keepIdentifier));
        return itemMapper.ItemToCreateItemResponse(item);
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
                    if (item.getUser().getId().equals(userDetails.getId())
                            || item.isKeepExpired()
                            || item.getKeepStatus().equals(KeepStatus.PULLOUT)
                            || item.getKeepStatus().equals(KeepStatus.DISUSED)
                    ) {
                        return itemMapper.ItemToItemInfoResponse(item);
                    }
                    return null;
                });
    }

    public List<ItemInfoResponse> getMyKeepItemInfoList(CustomUserDetails userDetails) {
        return itemRepository.findAllByUserIdAndKeepStatus(userDetails.getId(), KeepStatus.KEEP).stream()
                .map(itemMapper::ItemToItemInfoResponse)
                .collect(Collectors.toList());
    }

    public List<ItemInfoResponse> getAllExpiredKeepItemInfoList() {
        return itemRepository.findAllByKeepStatus(KeepStatus.KEEP).stream()
                .filter(Item::isKeepExpired)
                .map(itemMapper::ItemToItemInfoResponse)
                .collect(Collectors.toList());
    }

    public void pullOutItem(CustomUserDetails userDetails, PullOutItemsRequest pullOutItemsRequest) {
        pullOutItemsRequest.getKeepIdentifierList()
                .forEach(keepIdentifier -> {
                    Item item = itemRepository.findByKeepIdentifier(keepIdentifier)
                            .map(m -> {
                                if (m.isKeepExpired()) {
                                    m.changeKeepStatusToDisused();
                                } else if (m.isOwned(userDetails.getId())) {
                                    m.changeKeepStatusToPull();
                                }
                                return m;
                            })
                            .orElseThrow(() -> new RuntimeException(""));
                    itemRepository.save(item);
                });
    }

    private String generateKeepIdentifier() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

}
