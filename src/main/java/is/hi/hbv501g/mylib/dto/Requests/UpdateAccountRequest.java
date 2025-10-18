package is.hi.hbv501g.mylib.dto.Requests;

/*
This object is for updating Account information. currently only username and bio are here. any other account related
changes except for password are stored here.
 */
public class UpdateAccountRequest {
    private String username;
    private String bio;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
