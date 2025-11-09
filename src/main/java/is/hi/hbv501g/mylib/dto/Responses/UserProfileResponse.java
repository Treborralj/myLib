package is.hi.hbv501g.mylib.dto.Responses;

import java.util.List;

public class UserProfileResponse {
    private int id;
    private String username;
    private String bio;
    private String profilePictureBase64; 
    private List<PostResponse> posts;
    private List<ReviewResponse> reviews;
    private List<FollowResponse> followers;
    private List<FollowResponse> following;

    public UserProfileResponse(int id,
                               String username,
                               String bio,
                               String profilePictureBase64,
                               List<PostResponse> posts,
                               List<ReviewResponse> reviews,
                               List<FollowResponse> followers,
                               List<FollowResponse> following) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profilePictureBase64 = profilePictureBase64;
        this.posts = posts;
        this.reviews = reviews;
        this.followers = followers;
        this.following = following;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public String getProfilePictureBase64() { return profilePictureBase64; }
    public List<PostResponse> getPosts() { return posts; }
    public List<ReviewResponse> getReviews() { return reviews; }
    public List<FollowResponse> getFollowers() { return followers; }
    public List<FollowResponse> getFollowing() { return following; }
}
