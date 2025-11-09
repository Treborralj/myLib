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

@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(int id) {
        return bookRepository.findBookById(id);
    }

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

    @Override
    public Book addBook(Book book) {
        Book existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook != null) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        return bookRepository.save(book);
    }

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

    @Override
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBook(Book book) {
        if (book == null || !bookRepository.existsById(book.getId())) {
            throw new NoSuchElementException("Book not found with id: " + (book != null ? book.getId() : "null"));
        }
        bookRepository.delete(book);
    }

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

    @Override
    public BookResponse addBookFromRequest(CreateBookRequest body) {
        Book saved = addBook(body.getName(), body.getGenre(), body.getIsbn(), body.getWriter());
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findBooksAsResponses(Integer id,
                                                   String name,
                                                   String genre,
                                                   String isbn,
                                                   String writer,
                                                   Double score) {
        return findBooks(id, name, genre, isbn, writer, score)
                .stream()
                .map(this::toDto)
                .toList();
    }

}
