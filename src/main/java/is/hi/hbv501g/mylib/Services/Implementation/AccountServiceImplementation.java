package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdatePasswordRequest;
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
    public Account updateAccount(int id, UpdateAccountRequest dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (dto.getBio() != null && dto.getBio().equals(account.getBio())){
            account.setBio(dto.getBio());
        }
        if (dto.getUsername() != null && dto.getUsername().equals(account.getUsername())){
            account.setUsername(dto.getUsername());
        }
        if (dto.getProfilePic() != null && dto.getProfilePic().equals(account.getProfilePic())){
            account.setProfilePic(dto.getProfilePic());
        }

        accountRepository.save(account);
        return account;
    }
    @Override
    public Account createNewAccount(CreateAccountRequest dto){
        if (AccountRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("username is taken");
        }
        Account account =new Account(dto.getUsername(), dto.getPassword(), dto.getBio(), dto.getProfilePic());
        accountRepository.save(account);
        return account;
    }
    @Override
    /* add PasswordEncoder for hashing? method needs to get updated once hashing method is sorted out */
    public void updatePassword(int id, UpdatePasswordRequest dto){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        confirmNewPassword(dto.getNewPassword(), dto.getConfirmPassword());
        if(!dto.getOldPassword().equals(account.getPassword())){
            throw new RuntimeException("Current password is incorrect");
        }
        account.setPassword(dto.getNewPassword());
        accountRepository.save(account);

    }

    private void confirmNewPassword(String newPassword, String confirmPassword){
        if (newPassword == null || confirmPassword == null){
            throw new RuntimeException("Both password fields must be provided");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("New passwords don't match");
        }
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
