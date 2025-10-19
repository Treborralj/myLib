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
    public List<Book> getAllBooks() {
        return bookService.findAll();
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
    public List<Book> findBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) Double score) {

        return bookService.findBooks(name, genre, isbn, writer, score);
    }
    @PostMapping("/add")



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
    public Book addBook(
        @RequestParam String name,
        @RequestParam String genre,
        @RequestParam String isbn,
        @RequestParam String writer) {

        return bookService.addBook(name, genre, isbn, writer);
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
}
