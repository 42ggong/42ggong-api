package hdj.ggong.domain;

import hdj.ggong.common.enums.KeepStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "keep_identifier")
    private String keepIdentifier;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "keep_status")
    private KeepStatus keepStatus;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "keep_expiry_date")
    private LocalDateTime keepExpiryDate;

    @PrePersist
    public void onPrePersist() {
        updateKeepExpiryDate();
    }

    private void updateKeepExpiryDate() {
        if (user.isAccountNonPenalty()) {
            this.keepExpiryDate = createAt.plusDays(2);
        } else {
            this.keepExpiryDate = createAt.plusDays(1);
        }
    }

    public boolean isKeepExpired() {
        return keepExpiryDate.isBefore(LocalDateTime.now());
    }

    public void changeKeepStatusToPull() {
        this.keepStatus = KeepStatus.PULLOUT;
    }

    public void changeKeepStatusToDisused() {
        this.keepStatus = KeepStatus.DISUSED;
    }

    public boolean isOwned(Long userId) {
        if (this.user.getId().equals(userId)) {
            return true;
        } else {
            return false;
        }
    }
}
