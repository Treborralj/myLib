package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.ProfilePictureRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;
import is.hi.hbv501g.mylib.dto.Responses.FollowResponse;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Responses.SignInResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import is.hi.hbv501g.mylib.dto.Responses.UserProfileResponse;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import java.util.Base64;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Iterator;


@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Saves the given account to the repository.
     *
     * @param account the account to save
     * @return the saved account 
     */
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Deletes the given account from the repository.
     *
     * @param account the account to delete
     */
    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    /**
     * Updates the profile (username, bio) of the account belonging to the given username.
     *
     * @param username the username of the account to update
     * @param dto the account update payload
     * @return a DTO containing the updated account info
     */
    @Override
    public UpdateAccountResponse updateAccount(String username, UpdateAccountRequest dto) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (dto.getBio() != null && !dto.getBio().equals(account.getBio())) {
            account.setBio(dto.getBio());
        }
        if (dto.getUsername() != null && !dto.getUsername().equals(account.getUsername())) {
            account.setUsername(dto.getUsername());
        }

        Account updated = accountRepository.save(account);
        return new UpdateAccountResponse(updated.getId(), updated.getUsername(), updated.getBio());
    }

    /**
     * Stores a new profile picture for the given user.
     *
     * @param username the username of the account
     * @param dto a DTO containing the uploaded image
     * @return a DTO with the stored profile picture data
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

    /**
     * Fetches the profile picture of the given user.
     * The returned DTO contains the image as a Base64 string, if present.
     *
     * @param username the username of the account
     * @return a DTO with the user's profile picture
     */
    @Override
    public ProfilePictureResponse getProfilePicture(String username){
        Account account = accountRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("Account not found"));
        return new ProfilePictureResponse(account);
    }

    /**
     * Creates a new account from the provided signup data and saves it.
     * The password is encoded before persisting.
     *
     * @param dto the signup payload
     * @return the persisted account
     */
    @Override
    public Account createNewAccount(CreateAccountRequest dto){
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        return accountRepository.save(account);
    }

    
    /**
     * Checks whether an account exists with the given username.
     *
     * @param username the username to look up
     * @return true if an account exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    /**
     * Updates the password for the given user after verifying the current password
     * and confirming that the new passwords match.
     *
     * @param username the username of the account
     * @param dto the password change payload
     */
    @Override
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

    /**
     * Ensures that the two provided password values are both present and equal.
     *
     * @param newPassword the new password
     * @param confirmPassword the repeated new password
     * @throws RuntimeException if one is missing or they do not match
     */
    private void confirmNewPassword(String newPassword, String confirmPassword){
        if (newPassword == null || confirmPassword == null){
            throw new RuntimeException("Both password fields must be provided");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("New passwords don't match");
        }
    }

    /**
     * Returns all accounts in the system.
     *
     * @return list of all accounts
     */
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * Looks up an account by its username.
     *
     * @param username the username to search for
     * @return an Optional containing the account if found
     */
    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }


    /**
     * Finds accounts whose username contains the given string
     *
     * @param username the full or partial username to search for
     * @return list of matching accounts
     */
    @Override
    public List<Account> discoverAccountByUsername(String username){
        return accountRepository.findByUsernameContainingIgnoreCase(username);
    }


    /**
     * Validates the provided username and password against stored credentials.
     *
     * @param username the username to authenticate
     * @param password the raw password to check
     * @return a DTO with minimal account info if credentials are valid
     * @throws IllegalArgumentException if username is unknown or password is wrong
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
     * Returns the "want to read" list for the specified user.
     *
     * @param username the username of the account
     * @return list of books the user wants to read
     */
    @Transactional(readOnly = true)
    @Override
    public List<BookResponse> getWantToRead(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        return account.getWantToRead()
                .stream()
                .map(b -> new BookResponse(
                        b.getId(),
                        b.getName(),
                        b.getGenre(),
                        b.getIsbn(),
                        b.getWriter(),
                        b.getScore(),
                        java.util.List.of()
                ))
                .toList();
    }

    /**
     * Adds a book to the "want to read" list of the specified user.
     *
     * @param username the username of the account
     * @param book the book to add
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
     * Removes a book from the "want to read" list of the specified user.
     *
     * @param username the username of the account
     * @param bookId the id of the book to remove
     */
    @Transactional
    @Override
    public void removeBookFromWantToRead(String username, int bookId) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        Iterator<Book> it = account.getWantToRead().iterator();
        while (it.hasNext()) {
            Book b = it.next();
            if (b.getId() == bookId) {
                it.remove();
                break;
            }
        }

        accountRepository.save(account);
    }
    /**
     * Returns the "have read" list for the specified user.
     *
     * @param username the username of the account
     * @return list of books the user has finished
     */
    @Transactional(readOnly = true)
    @Override
    public List<BookResponse> getHaveRead(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        return account.getHaveRead()
                .stream()
                .map(b -> new BookResponse(
                        b.getId(),
                        b.getName(),
                        b.getGenre(),
                        b.getIsbn(),
                        b.getWriter(),
                        b.getScore(),
                        java.util.List.of()
                ))
                .toList();
    }

    /**
     * Adds a book to the "have read" list of the specified user.
     *
     * @param username the username of the account
     * @param book the book to add
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
     * Removes a book from the "have read" list of the specified user.
     *
     * @param username the username of the account
     * @param bookId the id of the book to remove
     */
    @Transactional
    @Override
    public void removeBookFromHaveRead(String username, int bookId) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        Iterator<Book> it = account.getHaveRead().iterator();
        while (it.hasNext()) {
            Book b = it.next();
            if (b.getId() == bookId) {
                it.remove();
                break;
            }
        }

        accountRepository.save(account);
    }

    /**
     * Returns the "currently reading" list for the specified user.
     *
     * @param username the username of the account
     * @return list of books the user is currently reading
     */
    @Transactional(readOnly = true)
    @Override
    public List<BookResponse> getAmReading(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        return account.getAmReading()
                .stream()
                .map(b -> new BookResponse(
                        b.getId(),
                        b.getName(),
                        b.getGenre(),
                        b.getIsbn(),
                        b.getWriter(),
                        b.getScore(),
                        java.util.List.of()
                ))
                .toList();
    }

    /**
     * Adds a book to the "currently reading" list of the specified user.
     *
     * @param username the username of the account
     * @param book the book to add
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
     * Removes a book from the "currently reading" list of the specified user.
     *
     * @param username the username of the account
     * @param bookId the id of the book to remove
     */
    @Transactional
    @Override
    public void removeBookFromAmReading(String username, int bookId) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        Iterator<Book> it = account.getAmReading().iterator();
        while (it.hasNext()) {
            Book b = it.next();
            if (b.getId() == bookId) {
                it.remove();
                break;
            }
        }

        accountRepository.save(account);
    }


    /**
     * Creates a follow relation where one user starts following another.
     *
     * @param followerName the username of the follower
     * @param followingName the username of the account to follow
     */
    @Transactional
    @Override
    public void followUser(String followerName, String followingName){
        if (Objects.equals(followerName, followingName)){
            throw new IllegalArgumentException( "no following yourself allowed");
        }
        Account follower = accountRepository.findByUsername(followerName).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        Account following = accountRepository.findByUsername(followingName).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        follower.getFollowing().add(following);
        accountRepository.save(follower);
    }


    /**
     * Removes a follow relation where one user was following another.
     *
     * @param followerName the username of the follower 
     * @param followingName the username of the account to unfollow
     */
    @Transactional
    @Override
    public void unfollowUser(String followerName, String followingName){
        if (Objects.equals(followerName, followingName)){
            throw new IllegalArgumentException( "unfollowing yourself not allowed, how'd you do it anyways?");
        }
        Account follower = accountRepository.findByUsername(followerName).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        Account following = accountRepository.findByUsername(followingName).
                orElseThrow(() -> new IllegalArgumentException("Username not found"));
        follower.getFollowing().remove(following);
        accountRepository.save(follower);
    }


    /**
     * Returns a list of accounts that the specified user is following.
     *
     * @param username the username whose followings to fetch
     * @return list of usernames wrapped in follow responses
     */
    @Override
    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowing(String username){
        List<Account> following = accountRepository.findFollowingOf(username);
        return following.stream()
                .map(a -> new FollowResponse(a.getUsername()))
                .toList();
    }


    /**
     * Deletes the account of the specified user after verifying their password.
     *
     * @param username the username of the account to delete
     * @param password the current password to verify
     * @throws ResponseStatusException if the user is not found or the password is wrong
     */
    @Override
    public void deleteAccount(String username, String password) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        accountRepository.delete(account);
    }


    /**
     * Returns a list of accounts that are following the specified user.
     *
     * @param username the username whose followers to fetch
     * @return list of usernames  
     */
    @Override
    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowers(String username){
        List<Account> followers = accountRepository.findFollowersOf(username);
        return followers.stream()
                .map(a -> new FollowResponse(a.getUsername()))
                .toList();
    }

    /**
     * Builds a full user profile response for the given username, including posts,
     * reviews, followers, following and profile picture.
     *
     * @param username the username whose profile to fetch
     * @return a user profile DTO
     */

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        List<PostResponse> posts = account.getPosts()
                .stream()
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getText(),
                        p.getTime()
                ))
                .toList();

        List<ReviewResponse> reviews = account.getReviews()
                .stream()
                .map(r -> new ReviewResponse(
                        r.getId(),
                        r.getText(),
                        r.getTime(),
                        r.getScore()
                ))
                .toList();

        List<FollowResponse> following = getFollowing(username);
        List<FollowResponse> followers = getFollowers(username);

        String profilePictureBase64 = null;
        if (account.getProfilePic() != null) {
                profilePictureBase64 = Base64.getEncoder().encodeToString(account.getProfilePic());
            }

            return new UserProfileResponse(
                    account.getId(),
                    account.getUsername(),
                    account.getBio(),
                    profilePictureBase64,
                    posts,
                    reviews,
                    followers,
                    following
        );
        }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getFeedFor(String username) {
        List<Account> following = accountRepository.findFollowingOf(username);

        return following.stream()
                .flatMap(acc -> acc.getPosts().stream())
                .sorted((p1, p2) -> p2.getTime().compareTo(p1.getTime()))
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getText(),
                        p.getTime()
                ))
                .toList();
    }

}
