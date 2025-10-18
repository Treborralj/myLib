package is.hi.hbv501g.mylib.dto.Requests;

/*
A small package for logging in. this item has a string for username and a string for password.
 */
public class SignInRequest {
    private String username;
    private String password;

    public SignInRequest(String username, String password){
        this.password = password;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
