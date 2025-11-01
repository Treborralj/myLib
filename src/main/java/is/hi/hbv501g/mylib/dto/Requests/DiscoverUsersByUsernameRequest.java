package is.hi.hbv501g.mylib.dto.Requests;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class DiscoverUsersByUsernameRequest {
    public int id;
    public String username;
    public String bio;

    public DiscoverUsersByUsernameRequest(int id, String username, String bio) {
        this.id = id;
        this.username = username;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
