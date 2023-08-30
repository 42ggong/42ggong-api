package hdj.ggong.domain;

import hdj.ggong.common.enums.KeepStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "keep_identifier")
    private String keepIdentifier;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "keep_status")
    private KeepStatus keepStatus;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
