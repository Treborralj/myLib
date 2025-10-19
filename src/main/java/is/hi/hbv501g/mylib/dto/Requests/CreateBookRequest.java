package is.hi.hbv501g.mylib.dto.Requests;

public class CreateBookRequest {
    private String name;
    private String genre;
    private String isbn;
    private String writer;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
}