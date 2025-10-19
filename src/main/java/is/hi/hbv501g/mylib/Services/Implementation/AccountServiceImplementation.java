package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Account account = accountRepository.findById(id);

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
        Account account = accountRepository.findById(id);
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

    @Transactional
    @Override
    public List<Book> getWantToRead(int accountId) {
        Account account = accountRepository.findById(accountId);
        System.out.println(account.getWantToRead());
        return account.getWantToRead();
    }
    @Transactional
    @Override
    public void addBookToWantToRead(int accountId, Book book) {
        Account account = accountRepository.findById(accountId);
        account.getWantToRead().add(book);
        accountRepository.save(account);
    }
    @Transactional
    @Override
    public void removeBookFromWantToRead(int accountId, int bookId) {
        Account account = accountRepository.findById(accountId);
        for(Book book : account.getWantToRead()) {
            if(book.getId() == bookId) {
                account.getWantToRead().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }
    @Transactional
    @Override
    public List<Book> getHaveRead(int accountId) {
        Account account = accountRepository.findById(accountId);
        System.out.println(account.getHaveRead());
        return account.getHaveRead();
    }
    @Transactional
    @Override
    public void addBookToHaveRead(int accountId, Book book) {
        Account account = accountRepository.findById(accountId);
        account.getHaveRead().add(book);
        accountRepository.save(account);
    }
    @Transactional
    @Override
    public void removeBookFromHaveRead(int accountId, int bookId) {
        Account account = accountRepository.findById(accountId);
        for(Book book : account.getHaveRead()) {
            if(book.getId() == bookId) {
                account.getHaveRead().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }
    @Transactional
    @Override
    public List<Book> getAmReading(int accountId) {
        Account account = accountRepository.findById(accountId);
        System.out.println(account.getAmReading());
        return account.getAmReading();
    }
    @Transactional
    @Override
    public void addBookToAmReading(int accountId, Book book) {
        Account account = accountRepository.findById(accountId);
        account.getAmReading().add(book);
        accountRepository.save(account);
    }
    @Transactional
    @Override
    public void removeBookFromAmReading(int accountId, int bookId) {
        Account account = accountRepository.findById(accountId);
        for(Book book : account.getAmReading()) {
            if(book.getId() == bookId) {
                account.getAmReading().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }
}
