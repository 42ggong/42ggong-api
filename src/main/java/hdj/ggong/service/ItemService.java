package hdj.ggong.service;

import hdj.ggong.common.enums.KeepStatus;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.User;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
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

    public Optional<ItemInfoResponse> searchItem(CustomUserDetails userDetails, String keepIdentifier) {
        return itemRepository.findByKeepIdentifier(keepIdentifier)
                .map(item -> {
                    if (item.getUser().getId().equals(userDetails.getId())
                            || item.isKeepExpired()
                            || item.getKeepStatus().equals(KeepStatus.STATUS_PULL)
                            || item.getKeepStatus().equals(KeepStatus.STATUS_DISUSED)
                    ) {
                        return itemMapper.ItemToItemInfoResponse(item);
                    }
                    return null;
                });
    }

    public List<ItemInfoResponse> getMyKeepItemInfoList(CustomUserDetails userDetails) {
        return itemRepository.findAllByUserIdAndKeepStatus(userDetails.getId(), KeepStatus.STATUS_KEEP).stream()
                .map(itemMapper::ItemToItemInfoResponse)
                .collect(Collectors.toList());
    }

    public List<ItemInfoResponse> getAllItemInfoList() {
        return itemRepository.findAll().stream()
                .map(itemMapper::ItemToItemInfoResponse)
                .collect(Collectors.toList());
    }

    private String generateKeepIdentifier() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

}
