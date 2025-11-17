package is.hi.hbv501g.mylib.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/******************************************************************************
 * @author Róbert A. Jack
 * E-mail : ral9@hi.is
 * Description : Service responsible for generating and validating JSON Web Tokens
 *
 *****************************************************************************/
@Service
public class JwtService {
    private final String secret;
    private final long expirationMs;
    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration}") long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    /**
     * Generates the cryptographic signing key used for creating and validating JWTs.
     * @return the HMAC SHA-256 key derived from the configured secret
     */
    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a signed JWT containing the given username as the subject.
     * @param username the username to embed in the token's subject claim
     * @return a signed JWT string
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a valid JWT.
     * @param token the JWT from which to extract the username
     * @return the username stored in the given token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
