package is.hi.hbv501g.mylib.Services;

import is.hi.hbv501g.mylib.dto.Requests.CreatePostRequest;
import is.hi.hbv501g.mylib.dto.Requests.UpdatePostRequest;
import is.hi.hbv501g.mylib.dto.Responses.PostResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/******************************************************************************
 * @author Emma Ófeigsdóttir
 * E-mail : emo16@hi.is
 * Description : Service Interface class for posts
 *
 *****************************************************************************/
public interface PostService {
    public PostResponse addPost(UserDetails me, CreatePostRequest request);
    public void deletePost(UserDetails me, int id);
    public PostResponse updatePost(UserDetails me, UpdatePostRequest dto);
    public List<PostResponse> getAccountPosts(String username);
}
