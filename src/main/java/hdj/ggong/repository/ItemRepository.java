package hdj.ggong.repository;

import hdj.ggong.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByKeepIdentifier(String keepIdentifier);
}
