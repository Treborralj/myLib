package is.hi.hbv501g.mylib.Controllers;

import is.hi.hbv501g.mylib.Services.PostService;
import is.hi.hbv501g.mylib.dto.Requests.CreatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePostRequest;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/******************************************************************************
 * @author Emma Ófeigsdóttir.
 * E-mail : emo16@hi.is
 * Description : Controller for posts.
 *
 *****************************************************************************/

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }



    /**
     * Creates a new post for the currently logged-in user.
     *
     * @param me the authenticated user
     * @param body the post creation payload
     * @return the created post
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponse addPost(@AuthenticationPrincipal UserDetails me, @ModelAttribute CreatePostRequest body) throws IOException {
        return postService.addPost(me, body);
    }


    /**
     * Deletes a post owned by the currently logged-in user.
     *
     * @param me the authenticated user
     * @param id the id of the post to delete
     */
    @DeleteMapping("/remove/{id}")
    public void deletePost(@AuthenticationPrincipal UserDetails me, @PathVariable int id) {
        postService.deletePost(me, id);
    }



    /**
     * Updates a post owned by the currently logged-in user.
     *
     * @param me the authenticated user
     * @param dto the post update payload
     * @return the updated post
     */
    @PatchMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponse updatePost(@AuthenticationPrincipal UserDetails me, @ModelAttribute UpdatePostRequest dto)throws IOException {
        return postService.updatePost(me, dto);
    }

    
    /**
     * Fetches all posts created by the given username.
     *
     * @param username the account whose posts to fetch
     * @return list of posts
     */
    @GetMapping("/account/{username}")
    public List<PostResponse> getAccountPosts(@PathVariable String username) {
        return postService.getAccountPosts(username);
    }


}
