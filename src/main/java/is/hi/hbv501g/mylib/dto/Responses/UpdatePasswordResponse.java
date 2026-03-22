package is.hi.hbv501g.mylib.dto.Responses;

public class UpdatePasswordResponse {
    private String message;

    public UpdatePasswordResponse() {
    }

    public UpdatePasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}