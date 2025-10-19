package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Book;
import is.hi.hbv501g.mylib.Persistence.Entities.Review;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Persistence.Repositories.BookRepository;
import is.hi.hbv501g.mylib.Persistence.Repositories.ReviewRepository;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


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

    @Transactional
    public Review addReview(CreateReviewRequest dto) {
        Account account = accountRepository.findById(dto.getAccountId());
        Book book = bookRepository.findBookById(dto.getBookId());
        Hibernate.initialize(book);
        Review review = new Review(dto.getText(), account, book, dto.getTime(), dto.getScore());
        account.getReviews().add(review);
        book.getReviews().add(review);
        review.setAccount(account);
        review.setBook(book);
        return reviewRepository.save(review);
    }


    @Transactional(readOnly = true)
    public List<Review> getAccountReviews(int accountId) {
        return reviewRepository.findAllByAccountIdFetch(accountId);
    }
}
