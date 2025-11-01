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
    public void deleteReview(@AuthenticationPrincipal UserDetails me, @PathVariable int id) {
        reviewService.deleteReview(me, id);
    }

    @PatchMapping("/edit")
    public ReviewResponse updateReview(@AuthenticationPrincipal UserDetails me, @RequestBody UpdateReviewRequest dto){
        return reviewService.updateReview(me, dto);
    }

    @GetMapping("/account/{username}")
    public List<ReviewResponse> getAccountReviews(@PathVariable String username) {
        return reviewService.getAccountReviews(username);
    }

    @GetMapping("/book/{id}")
    public List<ReviewResponse> getBookReviews(@PathVariable int id) {
        return reviewService.getBookReviews(id);
    }

    @GetMapping("/fetchAccount/{reviewId}")
    // nota UpdateAccountResponse í bili, hægt að búa til sérstakt response seinna ef
    // við viljum fyrir tilfellið þegar notandi er sóttur sem er ekki endilega maður sjálfur
    public UpdateAccountResponse fetchAccount(@PathVariable int reviewId) {
        return reviewService.fetchAccount(reviewId);
    }
}
