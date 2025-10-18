package is.hi.hbv501g.mylib.dto.Requests;
/*
A small data transfer package for creating accounts. It contains a String for a username and a string for password

 */
public class CreateAccountRequest {
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {
        this.password = password;
    }
}
