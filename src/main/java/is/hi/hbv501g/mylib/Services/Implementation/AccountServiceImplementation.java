package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public UpdateAccountResponse updateAccount(int id, UpdateAccountRequest dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (dto.getBio() != null && dto.getBio().equals(account.getBio())){
            account.setBio(dto.getBio());
        }
        if (dto.getUsername() != null && dto.getUsername().equals(account.getUsername())){
            account.setUsername(dto.getUsername());
        }

        Account updated = accountRepository.save(account);
        return new UpdateAccountResponse(updated.getId(), updated.getUsername(), updated.getBio());
    }

    @Override
    public void updateProfilePicture(int id, MultipartFile file) throws IOException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setProfilePic(file.getBytes());
        accountRepository.save(account);
    }
    @Override
    public ProfilePictureResponse getProfilePicture(int id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return new ProfilePictureResponse(account);
    }

    @Override
    public Account createNewAccount(CreateAccountRequest dto){
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        return accountRepository.save(account);
    }
    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
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
