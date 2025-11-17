package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.BookRepository;
import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.dto.Requests.CreateBookRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/******************************************************************************
 * @author Róbert A. Jack and Rúnar Sveinsson.
 * E-mail : ral9@hi.is and ras89@hi.is
 * Description : Implementation of the service interface for books
 *
 *****************************************************************************/
@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates and saves a new book from given parameters.
     * Checks that name and ISBN are present and that the ISBN is unique.
     *
     * @param name   the book's title
     * @param genre  the book's genre
     * @param isbn   the book's ISBN (must be unique)
     * @param writer the book's author
     * @return the saved book
     */
    @Override
    public Book addBook(String name, String genre, String isbn, String writer) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Book name is required.");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN is required.");
        }

        Book existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook != null) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }

        Book newBook = new Book(name, genre, isbn, writer, 0.0);
        return bookRepository.save(newBook);
    }

    /**
     * Finds books by filtering using the provided parameters.
     *
     * @param id      id
     * @param name    name 
     * @param genre   genre 
     * @param isbn    isbn 
     * @param writer  writer 
     * @param score   exact score
     * @return list of books matching the filters
     */
    @Override
    public List<Book> findBooks(Integer id,
                                String name,
                                String genre,
                                String isbn,
                                String writer,
                                Double score) {

        List<Book> all = bookRepository.findAll();

        return all.stream()
                .filter(b -> id == null || b.getId() == id)
                .filter(b -> name == null || containsIgnoreCase(b.getName(), name))
                .filter(b -> genre == null || containsIgnoreCase(b.getGenre(), genre))
                .filter(b -> isbn == null || containsIgnoreCase(b.getIsbn(), isbn))
                .filter(b -> writer == null || containsIgnoreCase(b.getWriter(), writer))
                .filter(b -> score == null || Double.compare(b.getScore(), score) == 0)
                .toList();
    }

    private boolean containsIgnoreCase(String field, String needle) {
        if (field == null) return false;
        return field.toLowerCase().contains(needle.toLowerCase());
    }

    /**
     * Deletes the book with the given id.
     *
     * @param id the id of the book to delete
     * @throws NoSuchElementException if the book does not exist
     */
    @Override
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Returns all books mapped to response DTOs.
     *
     * @return list of book response DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findAllAsResponses() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private BookResponse toDto(Book b) {
        var reviewTexts = (b.getReviews() == null)
                ? java.util.List.<String>of()
                : b.getReviews().stream()
                    .map(r -> r.getText() == null ? "" : r.getText())
                    .toList();

        return new BookResponse(
                b.getId(),
                b.getName(),
                b.getGenre(),
                b.getIsbn(),
                b.getWriter(),
                b.getScore(),
                reviewTexts
        );
    }


    /**
     * Creates a new book from the given request and returns it as a response DTO.
     *
     * @param body the create-book request payload
     * @return the created book as a DTO
     */
    @Override
    public BookResponse addBookFromRequest(CreateBookRequest body) {
        Book saved = addBook(body.getName(), body.getGenre(), body.getIsbn(), body.getWriter());
        return toDto(saved);
    }


    /**
     * Finds books using the same filters as {@link #findBooks} and maps them to response DTOs.
     *
     * @param id      id
     * @param name    name
     * @param genre   genre
     * @param isbn    isbn
     * @param writer  writer
     * @param score   score
     * @return list of matching book DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findBooksAsResponses(Integer id, String name, String genre, String isbn,
                                                   String writer, Double score) {
        return findBooks(id, name, genre, isbn, writer, score)
                .stream()
                .map(this::toDto)
                .toList();
    }

}
