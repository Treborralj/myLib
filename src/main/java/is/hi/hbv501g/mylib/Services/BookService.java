package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.dto.Requests.CreateBookRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;

import java.util.List;

/******************************************************************************
 * @author Rúnar Ágúst
 * E-mail: ras89@hi.is
 * Description : Service Interface class for books
 *
 *****************************************************************************/
public interface BookService {
    public Book addBook(String name, String genre, String isbn, String writer);

    public void deleteBook(int id);

    List<Book> findBooks(Integer id, String name, String genre, String isbn, String writer, Double score);

    List<BookResponse> findAllAsResponses();

    List<BookResponse> findBooksAsResponses(Integer id, String name, String genre, String isbn,
                                            String writer, Double score);

    BookResponse addBookFromRequest(CreateBookRequest body);
}
