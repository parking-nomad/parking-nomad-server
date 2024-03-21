package parkingnomad.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String upload(final MultipartFile multipartFile);
}
