package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    Book save(Book book);
    void delete(Book book);
    List<Book> findAll();
    List<Book> findBookByName(String name);
    Book findBookById(int id);


}
