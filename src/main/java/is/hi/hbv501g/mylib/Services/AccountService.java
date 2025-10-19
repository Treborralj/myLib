package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.ProfilePictureRequest;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.SignInResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.web.multipart.MultipartFile;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.dto.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.UpdatePasswordRequest;

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
    UpdateAccountResponse updateAccount(int id, UpdateAccountRequest dto);

    ProfilePictureResponse updateProfilePicture(int id, ProfilePictureRequest dto) throws IOException;

    ProfilePictureResponse getProfilePicture(int id);

    Account createNewAccount(CreateAccountRequest dto);

    boolean existsByUsername(String username);

    void updatePassword(int id, UpdatePasswordRequest dto);
    List<Account> findAll();
    Optional<Account> findByUsername(String username);
    SignInResponse login(String username, String password);

    void addBookToWantToRead(int accountId, Book book);
    void addBookToHaveRead(int accountId, Book book);
    void addBookToAmReading(int accountId, Book book);
    List<Book> getWantToRead(int accountId);
    List<Book> getHaveRead(int accountId);
    List<Book> getAmReading(int accountId);
    void removeBookFromHaveRead(int accountId, int bookId);
    void removeBookFromWantToRead(int accountId, int bookId);
    void removeBookFromAmReading(int accountId, int bookId);
}
