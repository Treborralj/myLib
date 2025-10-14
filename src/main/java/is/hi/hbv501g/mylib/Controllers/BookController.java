package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
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

    @RequestMapping("/")
    public String prufa(){
        return "Halló Heimur";
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    // GET /books/search?name=...&genre=...&isbn=...&writer=...&score=...
    @GetMapping("/search")
    public List<Book> findBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) Double score) {

        return bookService.findBooks(name, genre, isbn, writer, score);
    }
    @PostMapping("/add")

    public Book addBook(
        @RequestParam String name,
        @RequestParam String genre,
        @RequestParam String isbn,
        @RequestParam String writer) {

        return bookService.addBook(name, genre, isbn, writer);
    }

    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
