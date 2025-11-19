package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/******************************************************************************
 * @author Emma Ófeigsdóttir.
 * E-mail : emo16@hi.is
 * Description : Controller for posts.
 *
 *****************************************************************************/
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    /**
     * Creates a new review authored by the currently logged-in user.
     *
     * @param me the authenticated user
     * @param body the review creation payload
     * @return the created review
     */
    @PostMapping("/add")
    public ReviewResponse addReview(@AuthenticationPrincipal UserDetails me, @RequestBody CreateReviewRequest body) {
        return reviewService.addReview(me, body);
    }

    /**
     * Deletes a review if it belongs to the currently logged in user.
     *
     * @param me the authenticated user
     * @param id the review id
     */
    @DeleteMapping("/remove/{id}")
    public void deleteReview(@AuthenticationPrincipal UserDetails me, @PathVariable int id) {
        reviewService.deleteReview(me, id);
    }


    /**
     * Updates a review if it belongs to the currently logged in user.
     *
     * @param me the authenticated user
     * @param dto the review update payload
     * @return the updated review
     */
    @PatchMapping("/edit")
    public ReviewResponse updateReview(@AuthenticationPrincipal UserDetails me, @RequestBody UpdateReviewRequest dto){
        return reviewService.updateReview(me, dto);
    }


    /**
     * Lists all reviews written by the given account.
     *
     * @param username the account whose reviews to fetch
     * @return list of review DTOs
     */
    @GetMapping("/account/{username}")
    public List<ReviewResponse> getAccountReviews(@PathVariable String username) {
        return reviewService.getAccountReviews(username);
    }


    /**
     * Lists all reviews written for a specific book.
     *
     * @param id the book's id
     * @return list of review DTOs
     */
    @GetMapping("/book/{id}")
    public List<ReviewResponse> getBookReviews(@PathVariable int id) {
        return reviewService.getBookReviews(id);
    }


    /**
     * Fetches the account info for the author of the given review.

     * @param reviewId the id of the review
     * @return account info for the review's author
     */
    @GetMapping("/fetchAccount/{reviewId}")
    public UpdateAccountResponse fetchAccount(@PathVariable int reviewId) {
        return reviewService.fetchAccount(reviewId);
    }
}
