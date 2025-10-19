package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;

import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface ReviewService {

    Review addReview(CreateReviewRequest dto);

    List<Review> getAccountReviews(int accountId);
}
