package hdj.ggong.service;

import hdj.ggong.domain.Item;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.mapper.ItemMapper;
import hdj.ggong.repository.ItemRepository;
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public Item createItem(CustomUserDetails userDetails, CreateItemRequest createItemRequest) {
        return itemRepository.save(itemMapper.createItemRequestToItem(userDetails.getUser(), createItemRequest));
    }
}
