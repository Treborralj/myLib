package is.hi.hbv501g.mylib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/******************************************************************************
 * @author Róbert A. Jack
 * E-mail : ral9@hi.is
 * Description : Configuration class that defines security-related Spring beans.
 *
 *****************************************************************************/
@Configuration
public class SecurityBeans {
    /**
     * Creates a bean using the BCrypt hashing algorithm.
     * @return a BCrypt-based PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
