package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);

    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account login(Account account) {
        Account acc = findByUsername(account.getUsername());
        if(acc != null){
            if(acc.getPassword().equals(account.getPassword())){
                return acc;
            }
        }
        return null;
    }
}
