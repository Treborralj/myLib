package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.dto.Requests.CreatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.CreateReviewRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdateReviewRequest;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import is.hi.hbv501g.mylib.dto.Responses.ReviewResponse;
import is.hi.hbv501g.mylib.dto.Responses.UpdateAccountResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public interface PostService {
    public PostResponse addPost(UserDetails me, CreatePostRequest request);
    public void deletePost(UserDetails me, int id);
    public PostResponse updatePost(UserDetails me, UpdatePostRequest dto);
    public List<PostResponse> getAccountPosts(String username);
}
