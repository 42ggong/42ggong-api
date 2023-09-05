package hdj.ggong.domain;

import hdj.ggong.common.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "slack_id")
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "benefit_point")
    private int benefitPoint;

    @Column(name = "penalty_point")
    private int penaltyPoint;

    @Column(name = "is_account_non_penalty")
    private boolean isAccountNonPenalty;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    public void givenBenefitPoint(int point) {
        if (this.penaltyPoint >= point) {
            this.penaltyPoint -= point;
        } else {
            this.benefitPoint += (point - this.penaltyPoint);
            this.penaltyPoint = 0;
        }

        if (!this.isAccountNonPenalty && this.penaltyPoint == 0) {
            this.isAccountNonPenalty = true;
        }
    }

    public void givenPenaltyPoint() {
        this.penaltyPoint += 1;
        if (this.penaltyPoint >= 3) { // TODO: 하드 코딩. 패널티 조건
            this.isAccountNonPenalty = false;
        }
    }
}
