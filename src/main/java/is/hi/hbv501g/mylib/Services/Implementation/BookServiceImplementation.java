package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Repositories.BookRepository;
import is.hi.hbv501g.mylib.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/******************************************************************************
 * @author Rúnar Ágúst
 * Tölvupóstur: ras89@hi.is
 * Lýsing : 
 *
 *****************************************************************************/

@Service
public class BookServiceImplementation implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    /**
     * Retrieves a list of books matching the specified name.
     *
     * @param name the title of the book to search for.
     * @return a list of {@link Book} objects with the given name.
     */
    @Override
    public List<Book> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    

    /**
     * Retrieves all books stored in the database.
     *
     * @return a list of all {@link Book} entities.
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    /**
     * Finds a book by its ID.
     *
     * @param id the unique id of the book.
     * @return the {@link Book} entity with the matching ID, or {@code null} if not found.
     */
    @Override
    public Book findBookById(int id) {
        return bookRepository.findBookById(id);
    }




    /**
     * Creates and saves a new book in the database.
     *
     * Example:
     * <pre>
     * addBook("The Hobbit", "Fantasy", "1234567890", "J.R.R. Tolkien");
     * </pre>
     *
     * @param name   the title of the book (required).
     * @param genre  the genre of the book (required).
     * @param isbn   the unique ISBN number (required).
     * @param writer the author or writer of the book.
     * @return the newly created {@link Book} entity.
     * @throws IllegalArgumentException if required fields are missing or ISBN already exists.
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
     * Saves a {@link Book} object to the database
     *
     * @param book the {@link Book} entity to be added.
     * @return the saved {@link Book} entity.
     * @throws IllegalArgumentException if a bo ok with the same ISBN already exists.
     */
    @Override
    public Book addBook(Book book) {
        // Check if ISBN is unique
        Book existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook != null) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        return bookRepository.save(book);
    }


    /**
     * Searches for books that match the given optional parameters.
     * Any parameter that is {@code null} will be ignored in the search.
     *
     * Example:
     * <pre>
     * findBooks("Harry", "Fantasy", null, "Rowling", null);
     * </pre>
     * 
     * @param name  name or part of the book title. (optional)  
     * @param genre  optional genre of the book. (optional)
     * @param isbn   optional ISBN number. (optional)
     * @param writer optional writer’s name. (optional) 
     * @param score  optional score filter. (optional)
     * @return a list of {@link Book} objects matching the given parameters.
     */
    @Override
    public List<Book> findBooks(String name, String genre, String isbn, String writer, Double score) {
        return bookRepository.searchBooks(name, genre, isbn, writer, score);
    }
    



    /**
     * Deletes a book from the database based on the ID.
     *
     * @param id the ID of the book to be deleted.
     * @throws NoSuchElementException if no book with the given ID exists.
     */
    @Override
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }


    /**
     * Deletes the provided {@link Book} entity from the database.
     *
     * @param book the {@link Book} entity to be deleted.
     * @throws NoSuchElementException if the given book does not exist in the database.
     */
    @Override
    public void deleteBook(Book book) {
        if (book == null || !bookRepository.existsById(book.getId())) {
            throw new NoSuchElementException("Book not found with id: " + (book != null ? book.getId() : "null"));
        }
        bookRepository.delete(book);
    }
}
