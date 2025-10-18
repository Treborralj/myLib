package is.hi.hbv501g.mylib.dto.Responses;

public class CreateAccountResponse {
    private String message;
    private String username;
    private int id;

    public CreateAccountResponse(String message, String username, int id){
        this.message = message;
        this.username = username;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
