package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/******************************************************************************
 * @author Emma Ófeigsdóttir
 * E-mail : emo16@hi.is
 * Description : Service Interface class for reviews
 *
 *****************************************************************************/
public interface ReviewService {
    public ReviewResponse addReview(UserDetails me, CreateReviewRequest request);
    public void deleteReview(UserDetails me, int id);
    public ReviewResponse updateReview(UserDetails me, UpdateReviewRequest dto);
    public List<ReviewResponse> getAccountReviews(String username);
    public List<ReviewResponse> getBookReviews(int id);
    public UpdateAccountResponse fetchAccount(int reviewId);
}
