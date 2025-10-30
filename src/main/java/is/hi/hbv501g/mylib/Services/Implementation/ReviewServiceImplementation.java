package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Persistence.Repositories.BookRepository;
import is.hi.hbv501g.mylib.Persistence.Repositories.ReviewRepository;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ReviewServiceImplementation implements ReviewService {
    private ReviewRepository reviewRepository;
    private AccountRepository accountRepository;
    private BookRepository bookRepository;
    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, AccountRepository accountRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
    }

    private ReviewResponse toDto(Review r) {

        return new ReviewResponse(
                r.getId(),
                r.getText(),
                r.getAccount(),
                r.getBook(),
                r.getTime(),
                java.util.List.of(),
                r.getScore()
        );
    }

    @Override
    public ReviewResponse addReview(CreateReviewRequest request) {
        Book book = bookRepository.findBookById(request.getBookId());
        Account account = accountRepository.findById(request.getAccountId());
        LocalDateTime time = LocalDateTime.now();
        Review review = reviewRepository.save(new Review(request.getText(), account, book, time, request.getScore()));
        return toDto(review);
    }

    @Override
    public void deleteReview(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new NoSuchElementException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(UpdateReviewRequest dto) {
        Review review = reviewRepository.findReviewById(dto.getReviewId());
        if(dto.getText() != null) {review.setText(dto.getText());}
        review.setScore(dto.getScore());
        review.setComments(review.getComments());
        review.setAccount(review.getAccount());
        return toDto(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public List<Review> getAccountReviews(int id) {
        Account account = accountRepository.findById(id);
        return account.getReviews();
    }

    @Override
    @Transactional
    public List<Review> getBookReviews(int id) {
        Book book = bookRepository.findBookById(id);
        return book.getReviews();
    }

    @Override
    @Transactional
    public Account fetchAccount(int reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        return review.getAccount();
    }
}
