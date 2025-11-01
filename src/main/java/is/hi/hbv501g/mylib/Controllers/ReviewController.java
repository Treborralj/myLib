package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.Services.AccountService;
import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateBookRequest;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateAccountRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.BookResponse;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ReviewResponse addReview(@AuthenticationPrincipal UserDetails me, @RequestBody CreateReviewRequest body) {
        return reviewService.addReview(me, body);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }

    @PatchMapping("/edit")
    public ReviewResponse updateReview(@RequestBody UpdateReviewRequest dto){
        return reviewService.updateReview(dto);
    }

    @GetMapping("/account/{id}")
    public List<Review> getAccountReviews(@PathVariable int id) {
        return reviewService.getAccountReviews(id);
    }

    @GetMapping("/book/{id}")
    public List<Review> getBookReviews(@PathVariable int id) {
        return reviewService.getBookReviews(id);
    }

    @GetMapping("/fetchAccount/{reviewId}")
    public Account fetchAccount(@PathVariable int reviewId) {
        return reviewService.fetchAccount(reviewId);
    }
}
