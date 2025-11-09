package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account save(Account account);
    void delete(Account account);
    void deleteById(int id);
    List<Account> findAll();
    Optional<Account> findByUsername(String username);

    List<Account> findByUsernameContainingIgnoreCase(String username);

    Account findById(int id);
    static boolean existsByUsername(String username) {
        return false;
    }

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
