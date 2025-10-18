package is.hi.hbv501g.mylib.Persistence.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String bio;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] profilePic;

    @ManyToMany
    @JoinTable(name = "account_following", joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
    private List<Account> following = new ArrayList<>();
    @ManyToMany(mappedBy = "following")
    private List<Account> followers = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "account_have_read", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book>  haveRead = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "account_want_to_read", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book>  wantToRead = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "account_am_readin", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book>  amReading = new ArrayList<>();

    public Account(String username, String password, String bio) {
        this.username = username;
        this.password = password;
        this.bio = bio;
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

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
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

    public boolean isPresent() {
        return this != null;
    }
}
