package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    Book save(Book book);
    void delete(Book book);
    List<Book> findAll();
    List<Book> findBookByName(String name);
    Book findBookById(int id);
    
    Book findByIsbn(String isbn);






    /**
     * Searches books using optional filter parameters.
     * Each parameter can be null and only declared parameters will be included in the query.
     *
     *
     * Example:
     * <pre>
     * searchBooks("Harry", "Fantasy", null, "Rowling", null);
     * </pre>
     *
     * @param name  title or part of it. (optional)
     * @param genre genre or part of it. (optional)
     * @param isbn  ISBN or part of it. (optional)
     * @param writer writer’s name or part of it. (optional)
     * @param score score (exact match). (optional)
     * @return a list of {@link Book} entities matching the criteria.
     */
        @Query("""
        SELECT b FROM Book b
        WHERE (:name   IS NULL OR LOWER(b.name)   LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:genre  IS NULL OR LOWER(b.genre)  LIKE LOWER(CONCAT('%', :genre, '%')))
        AND (:isbn   IS NULL OR b.isbn          LIKE CONCAT('%', :isbn, '%'))
        AND (:writer IS NULL OR LOWER(b.writer) LIKE LOWER(CONCAT('%', :writer, '%')))
        AND (:score  IS NULL OR b.score = :score)
        """)
    List<Book> searchBooks(
        @Param("name") String name,
        @Param("genre") String genre,
        @Param("isbn") String isbn,
        @Param("writer") String writer,
        @Param("score") Double score
    );

}
