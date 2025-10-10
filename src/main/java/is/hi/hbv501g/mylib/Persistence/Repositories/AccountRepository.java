package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account save(Account account);
    void delete(Account account);
    List<Account> findAll();
    Account findByUsername(String username);
}
