package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.Requests.*;
import is.hi.hbv501g.mylib.dto.Responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /*
    SignupFixed is a new method for signup. A request is made to form according to CreateAccountRequest from JSON body
    it checks if a username is taken and if not, creates a new basic user before returning a status response
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signupFixed(@RequestBody CreateAccountRequest dto){
        if(accountService.existsByUsername(dto.getUsername())){
            return ResponseEntity.badRequest().body(Map.of("error", "Username is taken"));
        }

        Account account = accountService.createNewAccount(dto);

        CreateAccountResponse response = new CreateAccountResponse(
                "Account created successfully",
                account.getUsername(),
                account.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/discoverUser/{username}")
    public List<DiscoverUsersByUsernameRequest> discoverUser(@PathVariable String username) {
        return accountService.discoverAccountByUsername(username)
                .stream()
                .map(a -> new DiscoverUsersByUsernameRequest(a.getId(), a.getUsername(), a.getBio()))
                .toList();
    }

    /*
    This method takes an accounts id and an update request for said account. this cannot change passwords, see
    UpdatePassword() due to different handling. After updating account in repository a response is returned
     */
    @PatchMapping("/changeUsername")
    public ResponseEntity<?> updateAccountUsername(@AuthenticationPrincipal UserDetails me,
                                                   @RequestBody UpdateAccountRequest dto){
        UpdateAccountResponse response = accountService.updateAccount(me.getUsername(), dto);
        return ResponseEntity.ok(response);
    }

    /*
    This method compares an account id for a password update. it takes in a request and if successful, returns a response
     */
    @PatchMapping("/changePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal UserDetails me, @RequestBody UpdatePasswordRequest dto){
        accountService.updatePassword(me.getUsername(), dto);
        return ResponseEntity.ok("password updated succesfully");
    }

    /**
     *  Returns the wantToRead list of a loged in user
      * @param me
     * @return List<Book>
     */
    @GetMapping("/getWantToRead")
    public List<Book> getWantToRead(@AuthenticationPrincipal UserDetails me) {
        return accountService.getWantToRead(me.getUsername());
    }

    /**
     * Takes in an account id and a book and adds the book to the wantToRead list associated with the account
     * @param me
     * @param book
     */
    @PostMapping("/wantToReadAdd")
    public void addBookToWantToRead(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
        accountService.addBookToWantToRead(me.getUsername(), book);
    }

    /**
     * Takes in an account and a book id and removes the book from the wantToRead list associated with the account
     * @param me
     * @param bookId
     */
    @DeleteMapping("/wantToReadRemove/{bookId}")
    public void removeBookFromWantToRead(@AuthenticationPrincipal UserDetails me, @PathVariable int bookId){
        accountService.removeBookFromWantToRead(me.getUsername(), bookId);
    }
    /**
     *  Takes in an account id and returns the haveRead list associated with the account
     * @param me
     * @return List<Book>
     */
    @GetMapping("/haveRead/")
    public List<Book> getHaveRead(@AuthenticationPrincipal UserDetails me) {
        return accountService.getHaveRead(me.getUsername());
    }
    /**
     * Takes in an account id and a book and adds the book to the haveRead list associated with the account
     * @param me
     * @param book
     */
    @PostMapping("/haveReadAdd")
    public void addBookToHaveRead(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
        accountService.addBookToHaveRead(me.getUsername(), book);
    }
    /**
     * Takes in an account id and a book id and removes the book from the haveRead list associated with the account
     * @param me
     * @param bookId
     */
    @DeleteMapping("/haveReadRemove/{bookId}")
    public void removeBookFromHaveRead(@AuthenticationPrincipal UserDetails me, @PathVariable int bookId){
        accountService.removeBookFromHaveRead(me.getUsername(), bookId);
    }
    /**
     *  Takes in an account id and returns the amReading list associated with the account
     * @param me
     * @return List<Book>
     */
    @GetMapping("/amReading")
    public List<Book> getAmReading(@AuthenticationPrincipal UserDetails me) {
        return accountService.getAmReading(me.getUsername());
    }
    /**
     * Takes in an account id and a book and adds the book to the amReading list associated with the account
     * @param me
     * @param book
     */
    @PostMapping("/amReadingAdd")
    public void addBookToAmReading(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
        accountService.addBookToAmReading(me.getUsername(), book);
    }
    /**
     * Takes in an account id and a book id and removes the book from the amReading list associated with the account
     * @param me
     * @param bookId
     */
    @DeleteMapping("/amReadingRemove/{bookId}")
    public void removeBookFromAmReading(@AuthenticationPrincipal UserDetails me, @PathVariable int bookId){
        accountService.removeBookFromAmReading(me.getUsername(), bookId);
    }

    /**
     * Updates the Profile Picture of currently logged-in user
     * @param me the currently logged-in users credentials
     * @param dto a data transfer object containing the multipartfile for an image
     * @return a data transfer object containing the image in a 64bit String representation and account id
     */
    @PatchMapping("/updateProfilePicture")
    public ResponseEntity<?> updateProfilePicture(@AuthenticationPrincipal UserDetails me, @RequestBody ProfilePictureRequest dto) throws IOException {
        ProfilePictureResponse response = accountService.updateProfilePicture(me.getUsername(), dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches the Profile picture of a user
     * @param username the account to fetch the profile picture of
     * @return a data transfer object containing the image in a 64bit String representation and account id
     */
    @GetMapping("/getProfilePicture/{username}")
    public ResponseEntity<?> getProfilePicture(@PathVariable String username) {
        ProfilePictureResponse response = accountService.getProfilePicture(username);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a follow relation between the current user and a different account.
     * @param me the currently logged-in users credentials
     * @param dto a data transfer object containing a username
     * @return a confirmation a relation is created
     */
    @PostMapping("/followAccount")
    public ResponseEntity<?> follow(@AuthenticationPrincipal UserDetails me, @RequestBody FollowRequest dto){
        accountService.followUser(me.getUsername(), dto.getUsername());
        return ResponseEntity.ok("User is now following " + dto.getUsername());
    }

    /**
     * Removes a follow relation between the current user and a different account.
     * @param me the currently logged-in users credentials
     * @param dto a data transfer object containing a username
     * @return a confirmation a relation is removed
     */
    @PostMapping("/unfollowAccount")
    public ResponseEntity<?> unfollow(@AuthenticationPrincipal UserDetails me, @RequestBody FollowRequest dto){
        accountService.unfollowUser(me.getUsername(), dto.getUsername());
        return ResponseEntity.ok("User is no longer following " + dto.getUsername());

    }
    /**
     * Gets the names of all accounts a user is currently following
     * @param username the username of an account
     * @return A list of usernames that are followed
     */
    @GetMapping("/getFollowing/{username}")
    public ResponseEntity<?> getFollowing(@PathVariable String username){
        List<FollowResponse> response = accountService.getFollowers(username);
        return ResponseEntity.ok(response);
    }

    /**
     * Gets the names of all accounts currently following a user
     * @param username the username of an account
     * @return A list of usernames that are following
     */
    @GetMapping("/getFollowers/{username}")
    public ResponseEntity<?> getFollowers(@PathVariable String username){
        List<FollowResponse> response = accountService.getFollowers(username);
        return ResponseEntity.ok(response);
    }
}
