package webthuetro.user_service.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import webthuetro.user_service.validator.Unique;

import java.time.LocalDate;

@Data
@Builder
public class UserEditRequest {

    private MultipartFile avatar;
    @Unique(message = "Tên người dùng đã tồn tại")
    private String userName;
    private LocalDate birthDay;
    private Byte gender;
}
