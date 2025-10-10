package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.dto.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdatePasswordRequest;

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
    Account updateAccount(int id, UpdateAccountRequest dto);

    Account createNewAccount(CreateAccountRequest dto);

    void updatePassword(int id, UpdatePasswordRequest dto);
    List<Account> findAll();
    Account findByUsername(String username);
    Account login(Account account);

}
