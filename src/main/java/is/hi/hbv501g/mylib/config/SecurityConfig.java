package is.hi.hbv501g.mylib.config;

import is.hi.hbv501g.mylib.security.JwtAuthFilter;
import is.hi.hbv501g.mylib.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final MyUserDetailsService uds;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, MyUserDetailsService uds) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.uds = uds;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/account/signup","/books/**",
                                "/account/searchUser").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(uds)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
