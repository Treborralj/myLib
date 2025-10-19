package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.dto.Requests.CreateBookRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/******************************************************************************
 * @author Rúnar Ágúst
 * Tölvupóstur: ras89@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * Test endpoint
     * 
     * @return A message "Halló Heimur".
     */
    @RequestMapping("/")
    public String prufa(){
        return "Halló Heimur";
    }


    
    /**
     * Retrieves all books from the database.
     * 
     * @return a list of all {@link Book} objects.
     */
    @GetMapping("/all")
    public List<BookResponse> getAllBooks() {
        return bookService.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }


        /**
     * Searches for books that match the given parameters.
     * Any parameter missing will be ignored in the search filter.
     * 
     * Example: 
     * <pre>
     * GET /books/search?name=Harry&genre=Fantasy
     * </pre>
     *
     * @param name   the title or part of the title of the book (optional)
     * @param genre  the genre or part of the genre of the book (optional)
     * @param isbn   the ISBN number or part of it (optional)
     * @param writer the writer’s name or part of it (optional)
     * @param score  the exact score value (optional)
     * @return a list of {@link Book} objects matching the search criteria.
     */
    // GET /books/search?name=...&genre=...&isbn=...&writer=...&score=...
    @GetMapping("/search")
    public List<BookResponse> findBooks(
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) String isbn,
        @RequestParam(required = false) String writer,
        @RequestParam(required = false, name = "score") String scoreStr
    ) {
        String n = normalize(name);
        String g = normalize(genre);
        String i = normalize(isbn);
        String w = normalize(writer);
        Double s = parseScore(scoreStr);

        return bookService.findBooks(id, n, g, i, w, s) 
            .stream().map(this::toDto).toList();
    }

    private String normalize(String v) {
        if (v == null) return null;
        v = v.trim();
        return v.isEmpty() ? null : v;
    }

    private Double parseScore(String v) {
        if (v == null) return null;
        v = v.trim();
        if (v.isEmpty()) return null;
        try {
            return Double.valueOf(v);
        } catch (NumberFormatException e) {
            return null;
        }
    }



        /**
     * Adds a new book to the database.
     * 
     * Example:
     * <pre>
     * POST /books/add?name=TheHobbit&genre=Fantasy&isbn=123456&writer=Tolkien
     * </pre>
     *
     * @param name   the name of the book (required)
     * @param genre  the genre of the book (required)
     * @param isbn   the ISBN of the book (must be unique, required)
     * @param writer the writer of the book (required)
     * @return the created {@link Book} object.
     * @throws IllegalArgumentException if the book name or ISBN is missing or already exists.
     */
    @PostMapping("/add")
    public BookResponse addBook(@RequestBody CreateBookRequest body) {
        Book saved = bookService.addBook(
            body.getName(),
            body.getGenre(),
            body.getIsbn(),
            body.getWriter()
        );
        return toDto(saved); 
    }



        /**
     * Deletes a book from the database matching the id given
     * 
     * Example:
     * <pre>
     * DELETE /books/remove/5
     * </pre>
     *
     * @param id the ID of the book to delete.
     * @throws java.util.NoSuchElementException if no book with the given ID exists.
     */
    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    
    private BookResponse toDto(Book b) {
        /* 
        List<String> reviewTexts = (b.getReviews() == null)
                ? List.of()
                : b.getReviews().stream()
                .map(r -> r.getText() == null ? "" : r.getText())
                .toList();
    */
        return new BookResponse(
                b.getId(),
                b.getName(),
                b.getGenre(),
                b.getIsbn(),
                b.getWriter(),
                b.getScore(),
                java.util.List.of()
        );
    }
}
