package hdj.ggong.service;

import hdj.ggong.common.utils.TimeUtil;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.ItemHistory;
import hdj.ggong.domain.User;
import hdj.ggong.repository.ItemHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemHistoryService {

    private final ItemHistoryRepository itemHistoryRepository;
    private final TimeUtil timeUtil;

    public void recordHistory(User user, Item item) {
        ItemHistory itemHistory = ItemHistory.builder()
                .user(user)
                .item(item)
                .recordedDate(timeUtil.getCurrentDate())
                .keepStatus(item.getKeepStatus())
                .build();
        itemHistoryRepository.save(itemHistory);
    }
}
