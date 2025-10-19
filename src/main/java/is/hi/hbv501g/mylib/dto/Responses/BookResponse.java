package is.hi.hbv501g.mylib.dto.Responses;
import java.util.List;

public class BookResponse {
    private int id;
    private String name;
    private String genre;
    private String isbn;
    private String writer;
    private double score;
    private List<String> reviews; 
    public BookResponse(int id, String name, String genre, String isbn, String writer, double score, List<String> reviews) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.isbn = isbn;
        this.writer = writer;
        this.score = score;
        this.reviews = reviews;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getIsbn() { return isbn; }
    public String getWriter() { return writer; }
    public double getScore() { return score; }
    public List<String> getReviews() { return reviews; }
}
