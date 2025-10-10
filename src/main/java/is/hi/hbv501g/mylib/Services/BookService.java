package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;

import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface BookService {
    public List<Book> findBookByName(String name);
    public List<Book> findAll();
    public Book findBookById(int id);
    public Book addBook(Book book);
    public void deleteBook(Book book);
}
