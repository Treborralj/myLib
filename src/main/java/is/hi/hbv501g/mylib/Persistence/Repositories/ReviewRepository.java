package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/******************************************************************************
 * @author Emma Ófeigsdóttir
 * E-mail : emo16@hi.is
 * Description : Repository class for reviews
 *
 *****************************************************************************/
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review save(Review review);

    Review findReviewById(int id);

}
