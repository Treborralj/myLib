package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Review addReview(@RequestBody CreateReviewRequest dto) {
        return reviewService.addReview(dto);
    }

    @GetMapping("/account/{accountId}")
    public List<Review>  getAccountReviews(@PathVariable int accountId) {
        return reviewService.getAccountReviews(accountId);
    }
}
