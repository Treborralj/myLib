package is.hi.hbv501g.mylib.Persistence.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing :
 *
 *****************************************************************************/
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private int id;
    private String username;
    private String password;
    private String bio;
    private String profilePic;
    private List<Account> following;
    private List<Account> followers;
    private List<Book>  haveRead;
    private List<Book>  wantToRead;
    private List<Book>  amReading;

    public Account(int id, String username, String password, String bio, String profilePic, List<Account> following, List<Account> followers, List<Book> haveRead, List<Book> wantToRead, List<Book> amReading) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profilePic = profilePic;
        this.following = following;
        this.followers = followers;
        this.haveRead = haveRead;
        this.wantToRead = wantToRead;
        this.amReading = amReading;
    }

    public Account() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Account> getFollowing() {
        return following;
    }

    public void setFollowing(List<Account> following) {
        this.following = following;
    }

    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Account> followers) {
        this.followers = followers;
    }

    public List<Book> getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(List<Book> haveRead) {
        this.haveRead = haveRead;
    }

    public List<Book> getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(List<Book> wantToRead) {
        this.wantToRead = wantToRead;
    }

    public List<Book> getAmReading() {
        return amReading;
    }

    public void setAmReading(List<Book> amReading) {
        this.amReading = amReading;
    }
}
