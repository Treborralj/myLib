package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.ProfilePictureRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;
import is.hi.hbv501g.mylib.dto.Responses.FollowResponse;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.SignInResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import is.hi.hbv501g.mylib.dto.Responses.UserProfileResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface AccountService {
    Account save(Account account);
    void delete(Account account);
    UpdateAccountResponse updateAccount(String username, UpdateAccountRequest dto);

    ProfilePictureResponse updateProfilePicture(String username, ProfilePictureRequest dto) throws IOException;

    ProfilePictureResponse getProfilePicture(String username);

    Account createNewAccount(CreateAccountRequest dto);

    boolean existsByUsername(String username);

    void updatePassword(String username, UpdatePasswordRequest dto);
    List<Account> findAll();
    Optional<Account> findByUsername(String username);
    List<Account> discoverAccountByUsername(String partialUsername);
    SignInResponse login(String username, String password);


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
