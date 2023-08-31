package hdj.ggong.repository;

import hdj.ggong.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByKeepIdentifier(String keepIdentifier);
}
