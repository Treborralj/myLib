package is.hi.hbv501g.mylib.dto.Responses;

import java.time.LocalDateTime;

public class ReviewResponse {
    private int id;
    private int bookId;
    private int accountId;
    private String username;
    private String text;
    private LocalDateTime time;
    private double score;

    public ReviewResponse(int id, int bookId, int accountId, String username, String text, LocalDateTime time, double score) {
        this.id = id;
        this.bookId = bookId;
        this.accountId = accountId;
        this.username = username;
        this.text = text;
        this.time = time;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}