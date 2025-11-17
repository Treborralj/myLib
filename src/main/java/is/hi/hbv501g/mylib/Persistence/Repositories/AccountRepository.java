package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/******************************************************************************
 * @author Róbert A. Jack, Hálfdan Henrysson and Rúnar Sveinsson.
 * E-mail : ral9@hi.is, hah130@hi.is and ras89@hi.is
 * Description : Repository class for accounts
 *
 *****************************************************************************/
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    List<Account> findByUsernameContainingIgnoreCase(String username);

    @Query("""
       select f
       from Account a
       join a.following f
       where lower(a.username) = lower(:username)
       """)
    List<Account> findFollowingOf(@Param("username") String username);

    @Query("""
       select f
       from Account a
       join a.followers f
       where lower(a.username) = lower(:username)
       """)
    List<Account> findFollowersOf(@Param("username") String username);
}
