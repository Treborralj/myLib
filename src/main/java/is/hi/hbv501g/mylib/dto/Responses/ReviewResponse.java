package is.hi.hbv501g.mylib.dto.Responses;
import java.time.LocalDateTime;


public class ReviewResponse {
    private int id;
    private String text;
    private double score;
    private LocalDateTime time;
    private int accountId;
    private int bookId;

    public ReviewResponse(int id, String text, double score, LocalDateTime time, int accountId, int bookId) {
        this.id = id;
        this.text = text;
        this.score = score;
        this.time = time;
        this.accountId = accountId;
        this.bookId = bookId;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public double getScore() { return score; }
    public LocalDateTime getTime() { return time; }
    public int getAccountId() { return accountId; }
    public int getBookId() { return bookId; }
}
