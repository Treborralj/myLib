package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.ProfilePictureRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack, Hálfdan Henrysson and Rúnar Sveinsson.
 * E-mail : ral9@hi.is, hah130@hi.is and ras89@hi.is
 * Description : Service Interface class for accounts
 *
 *****************************************************************************/
public interface AccountService {
    UpdateAccountResponse updateAccount(String username, UpdateAccountRequest dto);

    ProfilePictureResponse updateProfilePicture(String username, ProfilePictureRequest dto) throws IOException;

    ProfilePictureResponse getProfilePicture(String username);

    Account createNewAccount(CreateAccountRequest dto);

    boolean existsByUsername(String username);

    void updatePassword(String username, UpdatePasswordRequest dto);

    List<Account> findAll();

    List<Account> discoverAccountByUsername(String partialUsername);

    void addBookToWantToRead(String username, Book book);

    void addBookToHaveRead(String username, Book book);

    void addBookToAmReading(String username, Book book);

    List<BookResponse> getWantToRead(String username);

    List<BookResponse> getHaveRead(String username);

    List<BookResponse> getAmReading(String username);

    void removeBookFromHaveRead(String username, int bookId);

    void removeBookFromWantToRead(String username, int bookId);

    void removeBookFromAmReading(String username, int bookId);

    @Transactional
    void followUser(String followerName, String followingName);

    @Transactional
    void unfollowUser(String followerName, String followingName);

    List<FollowResponse> getFollowers(String username);

    List<FollowResponse> getFollowing(String username);

    void deleteAccount(String username, String password);

    UserProfileResponse getUserProfile(String username);

    List<PostResponse> getFeedFor(String username);
}
