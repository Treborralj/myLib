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

        /**
         * Creates a new account from the JSON body.
         * Checks if the username already exists and returns 400 if it does.
         *
         * @param dto the signup payload
         * @return 201 with basic account info, or 400 if username is taken
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

        /**
         * Finds users whose username contains the given string.
         *
         * @param username the search string
         * @return a list of user dto (id, username, bio)
         */
        @GetMapping("/discoverUser/{username}")
        public List<DiscoverUsersByUsernameRequest> discoverUser(@PathVariable String username) {
            return accountService.discoverAccountByUsername(username)
                    .stream()
                    .map(a -> new DiscoverUsersByUsernameRequest(a.getId(), a.getUsername(), a.getBio()))
                    .toList();
        }


        /**
        * This method takes the account of the logged in user and an update request for said account. this cannot change passwords, see
        * UpdatePassword() due to different handling. After updating account in repository a response is returned
        * @param me the currently logged-in user's credentials
        * @param dto the account update payload
        * @return the updated account
        */
        @PatchMapping("/changeUsername")
        public ResponseEntity<?> updateAccountUsername(@AuthenticationPrincipal UserDetails me,
                                                    @RequestBody UpdateAccountRequest dto){
            UpdateAccountResponse response = accountService.updateAccount(me.getUsername(), dto);
            return ResponseEntity.ok(response);
        }

        /**
         * Changes the users password
         * @param me the currently logged-in users credentials
         * @param dto the password change request (old/new password)
         * @return 200 if updated successfully
        */
        @PatchMapping("/changePassword")
        public ResponseEntity<?> updatePassword(@AuthenticationPrincipal UserDetails me, @RequestBody UpdatePasswordRequest dto){
            accountService.updatePassword(me.getUsername(), dto);
            return ResponseEntity.ok("password updated succesfully");
        }

        /**
         *  Returns the wantToRead list of a logged in user
         * @param me the currently logged-in users credentials
        * @return List<BookResponse>
        */
    @GetMapping("/getWantToRead")
    public List<BookResponse> getWantToRead(@AuthenticationPrincipal UserDetails me) {
        return accountService.getWantToRead(me.getUsername());
    }

        /**
         * Takes in the logged in users account and a book and adds the book to the wantToRead list associated with the account
         * @param me the currently logged-in users credentials
         * @param book the book
         */
        @PostMapping("/wantToReadAdd")
        public void addBookToWantToRead(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
            accountService.addBookToWantToRead(me.getUsername(), book);
        }

        /**
         * Takes in the logged in users account  and a book id and removes the book from the wantToRead list associated with the account
         * @param me the currently logged-in users credentials
         * @param bookId the books id
         */
        @DeleteMapping("/wantToReadRemove/{bookId}")
        public void removeBookFromWantToRead(@AuthenticationPrincipal UserDetails me, @PathVariable int bookId){
            accountService.removeBookFromWantToRead(me.getUsername(), bookId);
        }
        /**
         *  Takes in the logged in users account  id and returns the haveRead list associated with the account
         * @param me the currently logged-in users credentials
        * @return List<BookResponse>
         */
        @GetMapping("/haveRead")
        public List<BookResponse> getHaveRead(@AuthenticationPrincipal UserDetails me) {
            return accountService.getHaveRead(me.getUsername());
        }

        /**
         * Takes in an the logged in users account  and a book and adds the book to the haveRead list associated with the account
         * @param me the currently logged-in users credentials
         * @param book
         */
        @PostMapping("/haveReadAdd")
        public void addBookToHaveRead(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
            accountService.addBookToHaveRead(me.getUsername(), book);
        }
        /**
         * Takes in an the logged in users account  and a book id and removes the book from the haveRead list associated with the account
         * @param me the currently logged-in users credentials
         * @param bookId
         */
        @DeleteMapping("/haveReadRemove/{bookId}")
        public void removeBookFromHaveRead(@AuthenticationPrincipal UserDetails me, @PathVariable int bookId){
            accountService.removeBookFromHaveRead(me.getUsername(), bookId);
        }
        /**
         *  Takes in an the log ged in users account  and returns the amReading list associated with the account
         * @param me the currently logged-in users credentials
        * @return List<BookResponse>

         */
        @GetMapping("/amReading")
        public List<BookResponse> getAmReading(@AuthenticationPrincipal UserDetails me) {
            return accountService.getAmReading(me.getUsername());
        }
        /**
         * Takes in an the logged in users account  and a book and adds the book to the amReading list associated with the account
         * @param me the currently logged-in users credentials
         * @param book
         */
        @PostMapping("/amReadingAdd")
        public void addBookToAmReading(@AuthenticationPrincipal UserDetails me, @RequestBody Book book){
            accountService.addBookToAmReading(me.getUsername(), book);
        }
        /**
         * Takes in an the logged in users account  and a book id and removes the book from the amReading list associated with the account
         * @param me the currently logged-in users credentials
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
        public ResponseEntity<List<FollowResponse>> getFollowing(@PathVariable String username) {
            return ResponseEntity.ok(accountService.getFollowing(username));
        }

        /**
         * Gets the names of all accounts currently following a user
         * @param username the username of an account
         * @return A list of usernames that are following
        */
        @GetMapping("/getFollowers/{username}")
        public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable String username) {
            return ResponseEntity.ok(accountService.getFollowers(username));
        }

        /**
         * Method that takes in a users password and deletes the users account
         * @param me the currently logged-in users credentials
         * @param password
         */
        @DeleteMapping("/deleteAccount")
        public void deleteAccount(@AuthenticationPrincipal UserDetails me, @RequestBody DeleteAccountRequest password){
            accountService.deleteAccount(me.getUsername(), password.getPassword());

        }



        /**
         * Method that returns all the users
         */
        @GetMapping("/getAll")
        public ResponseEntity<List<DiscoverUsersByUsernameRequest>> getAllAccounts() {
            List<DiscoverUsersByUsernameRequest> users = accountService.findAll()
                .stream()
                .map(a -> new DiscoverUsersByUsernameRequest(
                    a.getId(),
                    a.getUsername(),
                    a.getBio()
                ))
                .toList();

            return ResponseEntity.ok(users);
        }

        @GetMapping("/profile/{username}")
        public ResponseEntity<?> getUserProfile(@PathVariable String username) {
            return ResponseEntity.ok(accountService.getUserProfile(username));
        }


        /**
         * Method that returns the feed of an user (i.e the posts of the people they are following)
         * @param me the currently logged-in users credentials
         */
        @GetMapping("/feed")
        public ResponseEntity<?> getFeed(@AuthenticationPrincipal UserDetails me) {
            return ResponseEntity.ok(accountService.getFeedFor(me.getUsername()));
        }
    }
