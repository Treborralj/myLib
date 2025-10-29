package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.dto.Requests.SignInRequest;
import is.hi.hbv501g.mylib.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody SignInRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user.getUsername());
        return Map.of("token", token, "tokenType", "Bearer");
    }
    @GetMapping("/me")
    public Map<String, String> me(@AuthenticationPrincipal UserDetails me) {
        return Map.of("username", me.getUsername());
    }
}
