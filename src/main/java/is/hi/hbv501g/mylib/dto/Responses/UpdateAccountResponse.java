package is.hi.hbv501g.mylib.dto.Responses;

public class UpdateAccountResponse {
    private int id;
    private String username;
    private String bio;

    public UpdateAccountResponse(int id, String username, String bio) {
        this.id = id;
        this.username = username;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }
    public void setID(int id){
        this.id = id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
