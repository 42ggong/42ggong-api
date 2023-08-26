package hdj.ggong.security.jwt;

import hdj.ggong.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.access-token-expiry-minutes}")
    private Long accessTokenExpiryMinutes;

    @Value("${jwt.refresh-token-expiry-minutes}")
    private Long refreshTokenExpiryMinutes;

    @Value("${jwt.secret-key}")
    private byte[] bytes;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(bytes);
    }

    public String generateAccessTokenByUser(User user) {
        Date currentDate = getCurrentDate();
        Long expiryMinutes = this.accessTokenExpiryMinutes;
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(getExpiryDate(currentDate,  expiryMinutes))
                .setSubject(user.getUsername())
                .claim("role", user.getRole().getRoleName())
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshTokenByUser(User user) {
        Date currentDate = getCurrentDate();
        Long expiryMinutes = this.refreshTokenExpiryMinutes;
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(getExpiryDate(currentDate, expiryMinutes))
                .setSubject(user.getUsername())
                .signWith(secretKey)
                .compact();
    }

    private Date getCurrentDate() {
        return new Date();
    }

    private Date getExpiryDate(Date currentDate, Long expiryMinutes) {
        Long expiryMilliseconds = minutesToMilliseconds(expiryMinutes);
        return new Date(currentDate.getTime() + expiryMilliseconds);
    }

    private Long minutesToMilliseconds(Long minutes) {
        return minutes * 60 * 1000L;
    }
}
