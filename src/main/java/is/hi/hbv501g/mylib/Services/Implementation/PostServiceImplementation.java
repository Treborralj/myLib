package is.hi.hbv501g.mylib.Services.Implementation;

import is.hi.hbv501g.mylib.Persistence.Entities.Account;
import is.hi.hbv501g.mylib.Persistence.Entities.Post;
import is.hi.hbv501g.mylib.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.mylib.Persistence.Repositories.PostRepository;
import is.hi.hbv501g.mylib.Services.PostService;
import is.hi.hbv501g.mylib.dto.Requests.CreatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePostRequest;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostServiceImplementation implements PostService {
    private PostRepository postRepository;
    private AccountRepository accountRepository;

    @Autowired
    public PostServiceImplementation(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    private PostResponse toDto(Post p) {

        return new PostResponse(
                p.getId(),
                p.getText(),
                p.getTime()
        );
    }


    /**
     * Creates a new post for the logged in user.
     *
     * @param me the logged in user's details
     * @param request the post creation payload
     * @return the created post as a DTO
     */
    @Override
    public PostResponse addPost(UserDetails me, CreatePostRequest request) {
        Account account = accountRepository.findByUsername(me.getUsername()).
                orElseThrow(() -> new RuntimeException("Account not found"));
        LocalDateTime time = LocalDateTime.now();
        Post post = postRepository.save(new Post(request.getText(), account, time));
        return toDto(post);
    }


    /**
     * Deletes a post if it belongs to the logged in user.
     *
     * @param me the logged in user's details
     * @param id the id of the post to delete
     */
    @Override
    public void deletePost(UserDetails me, int id) {
        Post post = postRepository.findPostById(id);
        if(!post.getAccount().getUsername().equals(me.getUsername())) {
            throw new RuntimeException("Users can only delete their own posts");
        }
        postRepository.deleteById(id);
    }


    /**
     * Updates a post if it belongs to the logged in user.
     *
     * @param me the logged in user's details
     * @param dto the post update payload
     * @return the updated post as a DTO
     */
    @Override
    @Transactional
    public PostResponse updatePost(UserDetails me, UpdatePostRequest dto) {
        Post post = postRepository.findPostById(dto.getId());
        if(!post.getAccount().getUsername().equals(me.getUsername())) {
            throw new RuntimeException("Users can only edit their own posts");
        }
        if(dto.getText() != null) {post.setText(dto.getText());}
        post.setAccount(post.getAccount());
        return toDto(postRepository.save(post));
    }


    /**
     * Returns all posts belonging to the given username.
     *
     * @param username the account whose posts to fetch
     * @return list of post DTOs
     */
    @Override
    @Transactional
    public List<PostResponse> getAccountPosts(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getPosts()
                .stream()
                .map(this::toDto)
                .toList();
    }

}
