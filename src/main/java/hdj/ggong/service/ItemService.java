package hdj.ggong.service;

import hdj.ggong.domain.Item;
import hdj.ggong.domain.User;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
import hdj.ggong.exception.item.ItemNotExistException;
import hdj.ggong.mapper.ItemMapper;
import hdj.ggong.repository.ItemRepository;
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public ItemInfoResponse getItemInfo(Long itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::ItemToItemInfoResponse)
                .orElseThrow(() -> new ItemNotExistException(itemId));
    }

    private String generateKeepIdentifier() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
