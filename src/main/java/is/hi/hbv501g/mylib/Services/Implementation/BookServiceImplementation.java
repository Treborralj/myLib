package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.BookRepository;
import is.hi.hbv501g.mylib.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BookServiceImplementation implements BookService {

    private BookRepository bookRepository;

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
        // Check if ISBN is unique
        Book existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook != null) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBooks(String name, String genre, String isbn, String writer, Double score) {
        return bookRepository.searchBooks(name, genre, isbn, writer, score);
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
        bookRepository.delete(book);

    }
}
