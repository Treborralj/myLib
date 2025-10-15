package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.dto.Requests.CreateAccountRequest;
import is.hi.hbv501g.mylib.dto.Responses.ProfilePictureResponse;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePasswordRequest;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    void updateProfilePicture(int id, MultipartFile file) throws IOException;

    ProfilePictureResponse getProfilePicture(int id);

    Account createNewAccount(CreateAccountRequest dto);

    boolean existsByUsername(String username);

    void updatePassword(int id, UpdatePasswordRequest dto);
    List<Account> findAll();
    Account findByUsername(String username);
    Account login(Account account);

}
