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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UpdateAccountResponse updateAccount(String username, UpdateAccountRequest dto) {
        Account account = accountRepository.findByUsername(username)
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

    /*
    updateProfilePicture saves a profile picture to an account, throwing an error if it fails. returns a response entity
     */
    @Override
    public ProfilePictureResponse updateProfilePicture(String username, ProfilePictureRequest dto) throws IOException {
        MultipartFile file = dto.getFile();
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("Account not found"));

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
    public ProfilePictureResponse getProfilePicture(String username){
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("Account not found"));
        return new ProfilePictureResponse(account);
    }

    /*
    Creates a new account object and saves it to the repository
    */
    @Override
    public Account createNewAccount(CreateAccountRequest dto){
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
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
    public void updatePassword(String username, UpdatePasswordRequest dto){
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("Account not found"));

        confirmNewPassword(dto.getNewPassword(), dto.getConfirmPassword());

        if (!passwordEncoder.matches(dto.getOldPassword(), account.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        account.setPassword(encodedNewPassword);

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
    @Override
    public List<Account> discoverAccountByUsername(String partialUsername){
        return accountRepository.findByUsernameContainingIgnoreCase(partialUsername);
    }
    /*
    takes in a username and password and check if they match with the repository. if they do, it returns a response.
    This throws an error if either field do not mach with stored info.
     */
    @Override
    public SignInResponse login(String username, String password) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }
        return new SignInResponse(account.getId(), account.getUsername());
    }

    /**
     * Takes in a username and returns the wantToRead list associated with the account
     * @param username
     * @return
     */
    @Transactional
    @Override
    public List<Book> getWantToRead(String username) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        System.out.println(account.getWantToRead());
        return account.getWantToRead();
    }

    /**
     * Takes in an account id and a book and adds the book to the wantToRead list associated with the account
     * @param username
     * @param book
     */
    @Transactional
    @Override
    public void addBookToWantToRead(String username, Book book) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        account.getWantToRead().add(book);
        accountRepository.save(account);
    }

    /**
     * Takes in a username and a book id and removes the book from the wantToRead list associated with the account
     * @param username
     * @param bookId
     */
    @Transactional
    @Override
    public void removeBookFromWantToRead(String username, int bookId) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        for(Book book : account.getWantToRead()) {
            if(book.getId() == bookId) {
                account.getWantToRead().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }

    /**
     * Takes in an account id and returns the haveRead list associated with the account
     * @param username
     * @return
     */
    @Transactional
    @Override
    public List<Book> getHaveRead(String username) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        System.out.println(account.getHaveRead());
        return account.getHaveRead();
    }

    /**
     * Takes in an account id and a book and adds the book to the haveRead list associated with the account
     * @param username
     * @param book
     */
    @Transactional
    @Override
    public void addBookToHaveRead(String username, Book book) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        account.getHaveRead().add(book);
        accountRepository.save(account);
    }

    /**
     * Takes in an account id and a book id and removes the book from the haveRead list associated with the account
     * @param username
     * @param bookId
     */
    @Transactional
    @Override
    public void removeBookFromHaveRead(String username, int bookId) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        for(Book book : account.getHaveRead()) {
            if(book.getId() == bookId) {
                account.getHaveRead().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }

    /**
     * Takes in an account id and returns the amReading list associated with the account
     * @param username
     * @return
     */
    @Transactional
    @Override
    public List<Book> getAmReading(String username) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        System.out.println(account.getAmReading());
        return account.getAmReading();
    }

    /**
     * Takes in an account id and a book and adds the book to the amReading list associated with the account
     * @param username
     * @param book
     */
    @Transactional
    @Override
    public void addBookToAmReading(String username, Book book) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        account.getAmReading().add(book);
        accountRepository.save(account);
    }

    /**
     * Takes in an account id and a book id and removes the book from the haveRead list associated with the account
     * @param username
     * @param bookId
     */
    @Transactional
    @Override
    public void removeBookFromAmReading(String username, int bookId) {
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        for(Book book : account.getAmReading()) {
            if(book.getId() == bookId) {
                account.getAmReading().remove(book);
                break;
            }
        }
        accountRepository.save(account);
    }
}
