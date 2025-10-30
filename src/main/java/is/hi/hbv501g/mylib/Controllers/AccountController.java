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

    /*
    This method takes in an id and request info for changing profile picture. if this fails an error is thrown. a
    response is returned once finished
     */
    @PatchMapping("/updatePic")
    public ResponseEntity<?> updateProfilePicture(@AuthenticationPrincipal UserDetails me, ProfilePictureRequest dto) throws IOException {
        ProfilePictureResponse response = accountService.updateProfilePicture(me.getUsername(), dto);
        return ResponseEntity.ok(response);
    }

    /*
    this method fetches a profile picture from an account and returns in a response entity
     */
    @GetMapping("/myProfilePicture")
    public ResponseEntity<?> getProfilePicture(@AuthenticationPrincipal UserDetails me) {
        ProfilePictureResponse response = accountService.getProfilePicture(me.getUsername());
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a list of accounts matching the given string. If no account is found and empty list
     * is returned.
     * @param accountUsername
     * @return a list of accounts matching the given string.
     */
    @GetMapping("/searchUser")
    public List<Account> discoverUserByUsername(@RequestParam String accountUsername){
        return accountService.discoverAccountByUsername(accountUsername);
    }
}
