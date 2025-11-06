package is.hi.hbv501g.mylib.dto.Responses;

import java.time.LocalDateTime;

public class PostResponse {
    private int id;
    private String text;
    private LocalDateTime time;

    public PostResponse(int id, String text, LocalDateTime time) {
        this.id = id;
        this.text = text;
        this.time = time;
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

}
