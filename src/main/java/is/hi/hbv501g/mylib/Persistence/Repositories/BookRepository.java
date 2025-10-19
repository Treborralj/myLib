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
     * @param id the books id (optional)
     * @param name  title or part of it. (optional)
     * @param genre genre or part of it. (optional)
     * @param isbn  ISBN or part of it. (optional)
     * @param writer writer’s name or part of it. (optional)
     * @param score score (exact match). (optional)
     * @return a list of {@link Book} entities matching the criteria.
     */
    @Query(
    value = """
        SELECT *
        FROM books b
        WHERE (:id     IS NULL OR b.id = :id)
        AND (:name   IS NULL OR b.name   ILIKE CONCAT('%', :name, '%'))
        AND (:genre  IS NULL OR b.genre  ILIKE CONCAT('%', :genre, '%'))
        AND (:isbn   IS NULL OR b.isbn    LIKE CONCAT('%', :isbn, '%'))
        AND (:writer IS NULL OR b.writer ILIKE CONCAT('%', :writer, '%'))
        AND (:score  IS NULL OR b.score = :score)
    """,
    nativeQuery = true
    )
    List<Book> searchBooks(
    @Param("id") Integer id,
    @Param("name") String name,
    @Param("genre") String genre,
    @Param("isbn") String isbn,
    @Param("writer") String writer,
    @Param("score") Double score
    );

}
