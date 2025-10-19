package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review save(Review review);

    @Query("""
        select r
        from Review r
        join fetch r.account a
        join fetch r.book b
        where a.id = :accountId
    """)
    List<Review> findAllByAccountIdFetch(@Param("accountId") int accountId);
}
