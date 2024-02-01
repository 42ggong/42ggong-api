package hdj.ggong.service;

import hdj.ggong.common.utils.TimeUtil;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.ItemHistory;
import hdj.ggong.domain.User;
import hdj.ggong.dto.itemHistory.ItemHistoryResponse;
import hdj.ggong.mapper.ItemHistoryMapper;
import hdj.ggong.repository.ItemHistoryRepository;
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemHistoryService {

    private final ItemHistoryRepository itemHistoryRepository;
    private final TimeUtil timeUtil;
    private final ItemHistoryMapper itemHistoryMapper;

    public void recordHistory(User user, Item item) {
        ItemHistory itemHistory = ItemHistory.builder()
                .user(user)
                .item(item)
                .recordedDate(timeUtil.getCurrentDate())
                .keepStatus(item.getKeepStatus())
                .build();
        itemHistoryRepository.save(itemHistory);
    }

    public ItemHistoryResponse getItemHistoryListWithPage(CustomUserDetails userDetails, Integer page) {
        Page<ItemHistory> itemHistoryPage = itemHistoryRepository.findAllByOrderByRecordedDate(PageRequest.of(page, 30));

        return itemHistoryMapper.ItemHistoryPageToItemHistoryResponse(userDetails, itemHistoryPage);
    }
}
