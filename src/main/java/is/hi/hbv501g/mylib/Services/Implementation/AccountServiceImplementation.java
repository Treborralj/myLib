package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.ProfilePictureRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Responses.SignInResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;
    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    /*
    Save an account to the repository
     */
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /*
    removes an account from the repository
     */
    @Override
    public void delete(Account account) {
        accountRepository.delete(account);

    }

    /*
    Takes in an update request and updates any field that has been changed. returns a response.
     */
    @Override
    public UpdateAccountResponse updateAccount(int id, UpdateAccountRequest dto) {
        Account account = accountRepository.findById(id);

        if (dto.getBio() != null && dto.getBio().equals(account.getBio())){
            account.setBio(dto.getBio());
        }
        if (dto.getUsername() != null && dto.getUsername().equals(account.getUsername())){
            account.setUsername(dto.getUsername());
        }

        Account updated = accountRepository.save(account);
        return new UpdateAccountResponse(updated.getId(), updated.getUsername(), updated.getBio());
    }

    /*
    updateProfilePicture saves a profile picture to an account, throwing an error if it fails. returns a response entity
     */
    @Override
    public ProfilePictureResponse updateProfilePicture(int id, ProfilePictureRequest dto) throws IOException {
        MultipartFile file = dto.getFile();
        Account account = accountRepository.findById(id);

        try {
            account.setProfilePic(file.getBytes());
            accountRepository.save(account);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return new ProfilePictureResponse(account);
    }

    /*
    fetches the profile picture of an account. returns a response entity containing it converted to 64byte string.
     */
    @Override
    public ProfilePictureResponse getProfilePicture(int id){
        Account account = accountRepository.findById(id);
        return new ProfilePictureResponse(account);
    }

    /*
    Creates a new account object and saves it to the repository
    */
    @Override
    public Account createNewAccount(CreateAccountRequest dto){
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        return accountRepository.save(account);
    }

    /*
    checks if an entity exist in the repository based on username
     */
    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    @Override
    /*
    add PasswordEncoder for hashing? method needs to get updated once hashing method is sorted out
    */
    public void updatePassword(int id, UpdatePasswordRequest dto){
        Account account = accountRepository.findById(id);
        confirmNewPassword(dto.getNewPassword(), dto.getConfirmPassword());
        if(!dto.getOldPassword().equals(account.getPassword())){
            throw new RuntimeException("Current password is incorrect");
        }
        account.setPassword(dto.getNewPassword());
        accountRepository.save(account);

    }

    /*
    private method for confirming if two passwords match. Throws an error if they don't match
     */
    private void confirmNewPassword(String newPassword, String confirmPassword){
        if (newPassword == null || confirmPassword == null){
            throw new RuntimeException("Both password fields must be provided");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("New passwords don't match");
        }
    }

    /*
    Returns a list of all accounts
     */
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    /*
    finds a account based on username and returns that account. if it does not exist, it returns null
     */
    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    /*
    takes in a username and password and check if they match with the repository. if they do, it returns a response.
    This throws an error if either field do not mach with stored info.
     */
    @Override
    public SignInResponse login(String username, String password) {
        Optional<Account> acc = findByUsername(username);

        Account account = acc.orElseThrow(()->
            new IllegalArgumentException("Username not found"));

        // bad form, still need an encoder
        if (!Objects.equals(password, account.getPassword())){
            throw new IllegalArgumentException("Invalid Password");
        }

        return new SignInResponse(account.getId(), account.getUsername());
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
