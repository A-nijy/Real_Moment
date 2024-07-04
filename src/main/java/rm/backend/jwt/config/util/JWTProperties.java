package rm.backend.jwt.config.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Getter
public class JWTProperties {

    @Value("${jwt.access}")
    public String ACCESS_SECRET;   // 우리 서버만 알고 있는 비밀 값

    @Value("${jwt.refresh}")
    private String REFRESH_SECRET;

    private byte[] accessKeyBytes;
    private byte[] refreshKeyBytes;

    private Key ACCESS_KEY;
    private Key REFRESH_KEY;

    private final int ACCESS_TIME = 60000*30; // access Token 만료(1분 * 30)
    private final int REFRESH_TIME = 60000*60*24; // refresh Token 만료(1분 * 60 * 24)

    private final String TOKEN_PREFIX = "Bearer ";
    private final String ACCESS_STRING = "Access";
    private final String REFRESH_STRING = "Refresh";

    private final String ISSUER = "ADMIN_AN";

    @PostConstruct
    public void init() {
        this.accessKeyBytes = Decoders.BASE64.decode(ACCESS_SECRET);
        this.refreshKeyBytes = Decoders.BASE64.decode(REFRESH_SECRET);

        this.ACCESS_KEY = Keys.hmacShaKeyFor(accessKeyBytes);
        this.REFRESH_KEY = Keys.hmacShaKeyFor(refreshKeyBytes);
    }
}
