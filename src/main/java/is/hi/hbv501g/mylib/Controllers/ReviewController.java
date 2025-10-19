package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.Services.BookService;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;

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
    public ReviewResponse addReview(@RequestBody CreateReviewRequest dto) {
        Review review = reviewService.addReview(dto);
        return toDto(review);
    }

    @GetMapping("/account/{accountId}")
    public List<ReviewResponse> getAccountReviews(@PathVariable int accountId) {
        return reviewService.getAccountReviews(accountId)
                .stream()
                .map(this::toDto)
                .toList();
    }


    private ReviewResponse toDto(Review r) {
    return new ReviewResponse(
        r.getId(),
        r.getText(),
        r.getScore(),
        r.getTime(),
        (r.getAccount() != null ? r.getAccount().getId() : 0),
        (r.getBook() != null ? r.getBook().getId() : 0)
    );
}
}
