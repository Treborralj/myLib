package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;

import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface AccountService {
    Account save(Account account);
    void delete(Account account);
    List<Account> findAll();
    Account findByUsername(String username);
    Account login(Account account);

}
