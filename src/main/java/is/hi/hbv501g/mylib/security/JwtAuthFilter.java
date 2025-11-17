package is.hi.hbv501g.mylib.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/******************************************************************************
 * @author Róbert A. Jack
 * E-mail : ral9@hi.is
 * Description : JWT authentication filter that reads a Bearer token from the
 * Authorization header and sets the Spring Security context.
 *
 *****************************************************************************/
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService uds;

    public JwtAuthFilter(JwtService jwtService, MyUserDetailsService uds) {
        this.jwtService = jwtService;
        this.uds = uds;
    }

    /**
     *  Checks the Authorization header for a Bearer token and, if present,
     *  attempts to authenticate the associated user.
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param chain the remaining filter chain
     * @throws ServletException if an error occurs in the filter chain
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String username = jwtService.extractUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = uds.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken at =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    at.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(at);
                }
            } catch (Exception ignored) { }
        }
        chain.doFilter(request, response);
    }
}
