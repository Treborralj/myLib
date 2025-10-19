package is.hi.hbv501g.mylib.dto.Requests;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Comment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateReviewRequest {
    private String text;
    private LocalDateTime time;
    private double score;

    private int accountId;
    private int bookId;

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public LocalDateTime getTime() {return time;}
    public void setTime(LocalDateTime time) {this.time = time;}
    public double getScore() {return score;}
    public void setScore(double score) {this.score = score;}
    public int getAccountId() {return accountId;}
    public void setAccountId(int accountId) {this.accountId = accountId;}
    public int getBookId() {return bookId;}
    public void setBookId(int bookId) {this.bookId = bookId;}
}
