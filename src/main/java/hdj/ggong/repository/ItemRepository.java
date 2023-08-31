package hdj.ggong.repository;

import hdj.ggong.common.enums.KeepStatus;
import hdj.ggong.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByKeepIdentifier(String keepIdentifier);

    Optional<Item> findByKeepIdentifier(String keepIdentifier);

    List<Item> findAllByUserIdAndKeepStatus(Long userId, KeepStatus keepStatus);

    List<Item> findAllByKeepStatus(KeepStatus keepStatus);
}
