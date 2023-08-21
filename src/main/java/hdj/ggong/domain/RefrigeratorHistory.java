package hdj.ggong.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class RefrigeratorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id")
    private Item itemId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "history_tiem")
    private LocalDateTime historyTime;
}
