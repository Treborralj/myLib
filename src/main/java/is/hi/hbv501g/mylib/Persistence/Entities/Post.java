package is.hi.hbv501g.mylib.Persistence.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/******************************************************************************
 * @author Róbert A. Jack
 * E-mail : ral9@hi.is
 * Description : Entity class for posts
 *
 *****************************************************************************/
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String text;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    private byte[] image;
    @Column(name = "image_type")
    private String imageType;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private LocalDateTime time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Post(String title, String text, byte[] image, String imageType, Account account, LocalDateTime time) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.imageType = imageType;
        this.account = account;
        this.time = time;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
