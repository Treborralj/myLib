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
@Table(name = "books")
public class Book {
    @Id
    private int id;
    private String name;
    private String genre;
    private String isbn;
    private String writer;
    private double score;

    private List<Review> reviews;

    public Book(int id, String name, String genre, String isbn, String writer, double score) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.isbn = isbn;
        this.writer = writer;
        this.score = score;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
