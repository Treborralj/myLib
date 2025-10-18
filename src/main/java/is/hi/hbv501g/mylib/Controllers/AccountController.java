package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.Requests.*;
import is.hi.hbv501g.mylib.dto.Responses.CreateAccountResponse;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Responses.SignInResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /*
    @PostMapping("/signup")
    public Account signupPost(@RequestBody Account account){
        Account acc = accountService.findByUsername(account.getUsername());
        if(acc == null){
            accountService.save(account);
            return account;
        }
        //Það þarf að laga þetta, ákveða hvað gerist ef username er ekki laust // séð um í AccountServiceImplementation
        return new Account("account not available", "account not available", "account not available", "account not available");
    }*/

    // ný aðferð fyrir signup, er með error message núna og notar frekar Request/Response fyrir öryggi

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
    A login method. the Signin requests requires a username and password. if the username exists, the password is
    compared. if everything is a match, the response is returned
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginPost(@RequestBody SignInRequest dto, HttpSession session){
        SignInResponse response = accountService.login(dto.getUsername(), dto.getPassword());
        //SignInResponse response = new SignInResponse(acc.getId(), acc.getUsername());

        return ResponseEntity.ok(response);
    }

    /*
    this method invalidates all session info and logs the user out
     */
    @GetMapping("/logout")
    public String logoutGet(HttpSession session){
        session.invalidate();
        return "You have been logged out";
    }

     /*
     this method takes in the httpSession token and checks if a user is logged in or not
      */
    @GetMapping("/loggedin")
    public String loggedinGet(HttpSession session){
        Integer accountId = (Integer) session.getAttribute("LoggedInAccountId");
        if(accountId != null){
            return "you are logged in";
        }
        return "You are not logged in";
    }
    /*
    This method takes an accounts id and an update request for said account. this cannot change passwords, see
    UpdatePassword() due to different handling. After updating account in repository a response is returned
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody UpdateAccountRequest dto){
        UpdateAccountResponse response = accountService.updateAccount(id, dto);
        return ResponseEntity.ok(response);
    }

    /*
    This method compares an account id for a password update.. it takes in a request and if successful, returns a response
     */
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable int id, @RequestBody UpdatePasswordRequest dto){
        accountService.updatePassword(id, dto);
        return ResponseEntity.ok("password updated succesfully");
    }


    /*
    This method takes in a id and request info for changing profile picture. if this fails an error is thrown. a
    response is returned once finished
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProfilePicture(int id, ProfilePictureRequest dto) throws IOException {
        ProfilePictureResponse response = accountService.updateProfilePicture(id, dto);
        return ResponseEntity.ok(response);
    }

    /*
    this method fetches a profile picture from an account and returns in a response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfilePicture(@PathVariable int id) {
        ProfilePictureResponse response = accountService.getProfilePicture(id);
        return ResponseEntity.ok(response);
    }
}
