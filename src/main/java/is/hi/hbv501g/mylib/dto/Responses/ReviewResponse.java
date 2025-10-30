package is.hi.hbv501g.mylib.dto.Responses;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {
    private int id;
    private String text;
    private Account account;
    private Book book;
    private LocalDateTime time;
    private List<Comment> comments = new ArrayList<>();
    private double score;

    public ReviewResponse(int id, String text, Account account, Book book, LocalDateTime time, List<Comment> comments, double score) {
        this.id = id;
        this.text = text;
        this.account = account;
        this.book = book;
        this.time = time;
        this.comments = comments;
        this.score = score;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
