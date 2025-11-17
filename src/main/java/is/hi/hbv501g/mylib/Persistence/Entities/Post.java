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
    private String text;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private LocalDateTime time;

    public Post(String text, Account account, LocalDateTime time) {
        this.text = text;
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
