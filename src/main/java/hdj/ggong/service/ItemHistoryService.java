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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ItemHistoryResponse> getItemHistoryListWithPage(CustomUserDetails userDetails, Integer page) {
        if (page == null) {
            return itemHistoryRepository.findAllByOrderByRecordedDate().stream()
                    .map(itemHistory -> itemHistoryMapper.itemHistoryToItemHistoryResponse(itemHistory, userDetails.getRole()))
                    .collect(Collectors.toList());
        }
        return itemHistoryRepository.findAllByOrderByRecordedDate(PageRequest.of(page, 30)).stream()
                .map(itemHistory -> itemHistoryMapper.itemHistoryToItemHistoryResponse(itemHistory, userDetails.getRole()))
                .collect(Collectors.toList());
    }
}
