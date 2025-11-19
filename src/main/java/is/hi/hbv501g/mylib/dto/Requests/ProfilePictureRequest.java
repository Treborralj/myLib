package is.hi.hbv501g.mylib.dto.Requests;

import org.springframework.web.multipart.MultipartFile;
/*
A small data transfer file for profile pictures. it contains a Multipartfile storing a file.
Possibility for adding image type is here.
 */
public class ProfilePictureRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
