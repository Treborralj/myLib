package is.hi.hbv501g.mylib.dto.Responses;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;

import java.util.Base64;

public class ProfilePictureResponse {
    private String imageBase64;
    private String imageType;


    public ProfilePictureResponse(Account account){
        if (account.getProfilePic() != null){
            this.imageBase64 = Base64.getEncoder().encodeToString(account.getProfilePic());
        }
        this.imageType = account.getImageType();

    }


    public String getImageBase64() {
        return imageBase64;
    }
    public String getImageType() {
        return imageType;
    }
}
