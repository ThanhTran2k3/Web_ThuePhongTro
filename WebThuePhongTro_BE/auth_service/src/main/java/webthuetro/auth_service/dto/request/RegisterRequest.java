package webthuetro.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import webthuetro.auth_service.validator.Unique;

@Data
public class RegisterRequest {

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Pattern(regexp = "[0][0-9]*$", message = "Số điện thoại không hợp lệ")
    @Unique(message = "Số điện thoại đã tồn tại")
    private String phoneNumber;

    @NotBlank(message = "Email không được bỏ trống")
    @Email(message = "Email không hợp lệ")
    @Unique(message = "Email đã tồn tại")
    private String email;

    @NotBlank(message = "Mật khẩu không được bỏ trống")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=]).{8,}$", message = "Mật khẩu bắt đầu bằng chữ hoa, có 1 số và 1 kí tự đặc biệt")
    @Size(min = 8, message = "Mật khẩu phải từ 8 ký tự")
    private String password;

    @NotBlank(message = "Tên người dùng không được bỏ trống")
    private String userName;
}
