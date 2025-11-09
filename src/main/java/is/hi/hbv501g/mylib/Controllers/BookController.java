package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.dto.Requests.CreateBookRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/")
    public String prufa() {
        return "Halló Heimur";
    }

    /**
     * Retrieves all books from the database, mapped to DTOs.
     */
    @GetMapping("/all")
    public List<BookResponse> getAllBooks() {
        return bookService.findAllAsResponses();
    }

    /**
     * Searches for books that match the given parameters.
     * Any missing parameter is ignored.
     */
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

        return bookService.findBooksAsResponses(id, n, g, i, w, s);
    }

    @PostMapping("/add")
    public BookResponse addBook(@RequestBody CreateBookRequest body) {
        return bookService.addBookFromRequest(body);
    }

    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable int id) {
        bookService.deleteBook(id);
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
}
