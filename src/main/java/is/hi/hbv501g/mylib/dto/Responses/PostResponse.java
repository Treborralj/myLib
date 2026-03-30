package is.hi.hbv501g.mylib.dto.Responses;

import java.time.LocalDateTime;

public class PostResponse {
    private int id;
    private String username;
    private String title;
    private String text;
    private LocalDateTime time;
    private String imageBase64;
    private String imageType;

    public PostResponse(
            int id,
            String username,
            String title,
            String text,
            LocalDateTime time,
            String imageBase64,
            String imageType
    ) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.text = text;
        this.time = time;
        this.imageBase64 = imageBase64;
        this.imageType = imageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
