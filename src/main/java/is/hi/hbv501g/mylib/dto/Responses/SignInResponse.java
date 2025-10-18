package is.hi.hbv501g.mylib.dto.Responses;

public class SignInResponse {
    private int id;
    private String username;

    public SignInResponse(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
