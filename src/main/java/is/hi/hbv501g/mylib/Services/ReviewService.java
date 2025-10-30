package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;

import java.time.LocalDateTime;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface ReviewService {
    public ReviewResponse addReview(CreateReviewRequest request);
    public void deleteReview(int id);
    public ReviewResponse updateReview(UpdateReviewRequest dto);
    public List<Review> getAccountReviews(int id);
    public List<Review> getBookReviews(int id);
    public Account fetchAccount(int reviewId);
}
