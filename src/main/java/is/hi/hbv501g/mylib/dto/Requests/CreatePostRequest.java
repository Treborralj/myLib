package is.hi.hbv501g.mylib.dto.Requests;

import org.springframework.web.multipart.MultipartFile;

public class CreatePostRequest {
    private String title;
    private String text;
    private MultipartFile file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setText(String text) { this.text = text; }

    public String getText() { return text; }
}
