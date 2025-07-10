package webthuetro.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import webthuetro.auth_service.validator.Unique;

@Data
public class EditAccountRequest {

    @Email(message = "Email không hợp lệ")
    @Unique(message = "Email đã tồn tại")
    private String email;

    @Pattern(regexp = "[0][0-9]*$", message = "Số điện thoại không hợp lệ")
    @Unique(message = "Số điện thoại đã tồn tại")
    private String phoneNumber;
}
