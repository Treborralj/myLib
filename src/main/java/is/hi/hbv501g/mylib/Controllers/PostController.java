package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Services.PostService;
import is.hi.hbv501g.mylib.Services.ReviewService;
import is.hi.hbv501g.mylib.dto.Requests.CreatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public PostResponse addPost(@AuthenticationPrincipal UserDetails me, @RequestBody CreatePostRequest body) {
        return postService.addPost(me, body);
    }

    @DeleteMapping("/remove/{id}")
    public void deletePost(@AuthenticationPrincipal UserDetails me, @PathVariable int id) {
        postService.deletePost(me, id);
    }

    @PatchMapping("/edit")
    public PostResponse updatePost(@AuthenticationPrincipal UserDetails me, @RequestBody UpdatePostRequest dto){
        return postService.updatePost(me, dto);
    }

    @GetMapping("/account/{username}")
    public List<PostResponse> getAccountPosts(@PathVariable String username) {
        return postService.getAccountPosts(username);
    }


}
