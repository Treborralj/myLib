package is.hi.hbv501g.mylib.dto.Requests;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePictureRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
