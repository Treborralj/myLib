package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.dto.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdatePasswordRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public Account signupPost(@RequestBody Account account){
        Account acc = accountService.findByUsername(account.getUsername());
        if(acc == null){
            accountService.save(account);
            return account;
        }
        //Það þarf að laga þetta, ákveða hvað gerist ef username er ekki laust // séð um í AccountServiceImplementation
        return new Account("account not available", "account not available", "account not available", "account not available");

    }
    @PostMapping("/login")
    public String loginPost(@RequestBody Account account, HttpSession session){
        Account acc = accountService.login(account);
        if(acc != null){
            session.setAttribute("LoggedInAccountId", acc.getId());
            return "Login was succesfull";
        }
        return "login failed";
    }
    @GetMapping("/logout")
    public String logoutGet(HttpSession session){
        session.invalidate();
        return "You have been logged out";
    }
    @GetMapping("/loggedin")
    public String loggedinGet(HttpSession session){
        Integer accountId = (Integer) session.getAttribute("LoggedInAccountId");
        if(accountId != null){
            return "you are logged in";
        }
        return "You are not logged in";
    }
    @PatchMapping("/{id}")
    public Account updateAccount(@PathVariable int id, @RequestBody UpdateAccountRequest dto){
        return accountService.updateAccount(id, dto);
    }
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable int id, @RequestBody UpdatePasswordRequest dto){
        accountService.updatePassword(id, dto);
        return ResponseEntity.ok("password updated succesfully");
    }


}
