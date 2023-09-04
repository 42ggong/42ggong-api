package hdj.ggong.repository;

import hdj.ggong.domain.ItemHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemHistoryRepository extends JpaRepository<ItemHistory, Long> {

    List<ItemHistory> findAllByOrderByRecordedDate();

    List<ItemHistory> findAllByOrderByRecordedDate(PageRequest pageRequest);

}
