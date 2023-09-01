package hdj.ggong.domain;

import hdj.ggong.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "benefit_point")
    private Long benefitPoint;

    @Column(name = "is_account_non_penalty")
    private boolean isAccountNonPenalty;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    public void givenPenalty() {
        this.isAccountNonPenalty = false;
    }

    public void lock() {
        this.isAccountNonPenalty = true;
        this.isAccountNonLocked = false;
    }

    public void unlock() {
        this.isAccountNonPenalty = true;
    }
}
