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
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/******************************************************************************
 * @author Emma Ófeigsdóttir
 * E-mail : emo16@hi.is
 * Description : Implementation of the service interface for reviews
 *
 *****************************************************************************/
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
                r.getTime(),
                r.getScore()
        );
    }


    /**
     * Creates a new review for the given book, authored by the logged-in user.
     *
     * @param me the logged-in user's details
     * @param request the review creation payload (text, score, book id)
     * @return the created review as a DTO
     * @throws IllegalArgumentException if the account or book cannot be found
     */
    @Override
    public ReviewResponse addReview(UserDetails me, CreateReviewRequest request) {
        Book book = bookRepository.findBookById(request.getBookId());
        Account account = accountRepository.findByUsername(me.getUsername()).
                orElseThrow(() -> new RuntimeException("Account not found"));
        LocalDateTime time = LocalDateTime.now();
        Review review = reviewRepository.save(new Review(request.getText(), account, book, time, request.getScore()));
        return toDto(review);
    }


    /**
     * Deletes a review if it belongs to the logged-in user.
     *
     * @param me the logged-in user's details
     * @param id the id of the review to delete
     * @throws RuntimeException if the review is not owned by the user
     */
    @Override
    public void deleteReview(UserDetails me, int id) {
        Review review = reviewRepository.findReviewById(id);
        if(!review.getAccount().getUsername().equals(me.getUsername())) {
            throw new RuntimeException("Users can only delete their own reviews");
        }
        reviewRepository.deleteById(id);
    }


    /**
     * Updates a review if it belongs to the logged-in user.
     *
     * @param me the logged-in user's details
     * @param dto the review update payload
     * @return the updated review as a DTO
     * @throws RuntimeException if the review is not owned by the user
     */
    @Override
    @Transactional
    public ReviewResponse updateReview(UserDetails me, UpdateReviewRequest dto) {
        Review review = reviewRepository.findReviewById(dto.getReviewId());
        if(!review.getAccount().getUsername().equals(me.getUsername())) {
            throw new RuntimeException("Users can only edit their own reviews");
        }
        if(dto.getText() != null) {review.setText(dto.getText());}
        review.setScore(dto.getScore());
        review.setAccount(review.getAccount());
        return toDto(reviewRepository.save(review));
    }

    /**
     * Returns all reviews written by the given username.
     *
     * @param username the account whose reviews to fetch
     * @return list of review DTOs
     */
    @Override
    @Transactional
    public List<ReviewResponse> getAccountReviews(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        return account.getReviews()
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Returns all reviews written for the given book id.
     *
     * @param id the id of the book
     * @return list of review DTOs
     * @throws IllegalArgumentException if the book does not exist
     */
    @Override
    @Transactional
    public List<ReviewResponse> getBookReviews(int id) {
        Book book = bookRepository.findBookById(id);

        return book.getReviews()
                .stream()
                .map(this::toDto)
                .toList();
    }


    /**
     * Fetches the account info of the author of the given review.
     *
     * @param reviewId the id of the review
     * @return account info for the review's author
     */
    @Override
    @Transactional
    public UpdateAccountResponse fetchAccount(int reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        Account acc = review.getAccount();
        return new UpdateAccountResponse(acc.getId(),acc.getUsername(),acc.getBio());
    }
}
