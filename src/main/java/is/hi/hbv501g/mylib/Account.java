package is.hi.hbv501g.mylib;

public class Account {
    private String username;
    private String bio;

    public Account(String username, String bio) {
        this.username = username;
        this.bio = bio;
    }
    String getUsername() {
        return username;
    }

    void setUsername(String username){
        this.username = username;
    }

    private String getBio() {
        return bio;
    }

    void setBio(String bio) {
        this.bio = bio;
    }

}
