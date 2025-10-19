package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;

import java.util.List;

/******************************************************************************
 * @author Rúnar Ágúst
 * Tölvupóstur: ras89@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface BookService {
    public List<Book> findBookByName(String name);
    public List<Book> findAll();
    public Book findBookById(int id);
    public Book addBook(String name, String genre, String isbn, String writer);
    public Book addBook(Book book);

    public void deleteBook(int id);
    public void deleteBook(Book book);
    List<Book> findBooks(Integer id,String name, String genre, String isbn, String writer, Double score);

}
