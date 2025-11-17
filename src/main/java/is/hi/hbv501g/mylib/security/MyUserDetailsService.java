package is.hi.hbv501g.mylib.security;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/******************************************************************************
 * @author Róbert A. Jack
 * E-mail : ral9@hi.is
 * Description : Service class used by Spring Security to load user-specific
 * data during authentication.
 *
 *****************************************************************************/
@Service
public class MyUserDetailsService implements UserDetailsService {
    private AccountRepository accountRepository;

    public MyUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Loads a user's authentication details based on their username.
     * @param username the username of the account to look up
     * @return an object containing the user's credentials
     * and granted authorities
     * @throws UsernameNotFoundException if no user with the given username exists
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .authorities("USER")
                .build();
    }
}
