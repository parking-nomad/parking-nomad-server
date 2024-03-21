package parkingnomad.adaptor.out.imageuploader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import parkingnomad.exception.InvalidFileNameException;
import parkingnomad.exception.InvalidFileTypeException;
import parkingnomad.support.BaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class S3ImageUploaderTest extends BaseTest {

    public static final String PATH = new File("src/test/resources/coffee.png").getAbsolutePath();


    @Autowired
    S3ImageUploader s3ImageUploader;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", "   ", "fileWithoutExtension"})
    @DisplayName("파일의 이름이 비어있거나, 확장자가 존재하지 않는 경우 예외가 발생한다.")
    void uploadFailByFileName(final String invalidFileName) throws IOException {
        //given
        final FileInputStream fileInputStream = new FileInputStream(PATH);
        MultipartFile file = new MockMultipartFile("images", invalidFileName, "image/webp", fileInputStream);

        //when & then
        assertThatThrownBy(() -> s3ImageUploader.upload(file))
                .isInstanceOf(InvalidFileNameException.class)
                .hasMessageContaining("파일의 이름이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("파일의 contentType이 image/webp가 아닌 경우 예외가 발생한다.")
    void uploadFailByInvalidContentType() throws IOException {
        //given
        final FileInputStream fileInputStream = new FileInputStream(PATH);
        MultipartFile file = new MockMultipartFile("images", "images.webp", "image/png", fileInputStream);

        //when & then
        assertThatThrownBy(() -> s3ImageUploader.upload(file))
                .isInstanceOf(InvalidFileTypeException.class)
                .hasMessageContaining("webp형식의 이미지만 업로드 가능합니다.");
    }
}
