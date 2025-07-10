package webthuetro.otp_service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ERROR_USER_LOGIN(9999, "Tên người dùng hoặc mật khẩu không hợp lệ"),
    ERROR_USER_REGISTER(9997, "Tạo tài khoản thất bại"),
    BLOCK_ACCOUNT(9998, "Tài khoản đang bị khóa"),
    ERROR_CHANGE_PASSWORD(9997, "Mật khẩu hiện tại không đúng"),
    ERROR_SEND_EMAIL(9996, "Lỗi gửi xác thực OTP"),
    ERROR_OTP(9995, "OTP không hợp lệ"),
    USER_EXIST(1001, "Người dùng đã tồn tại"),
    USER_NOT_EXIST(1002, "Người dùng không tồn tại"),
    USER_403(403, "Người dùng không có quyền truy cập"),
    ERROR_USERNAME(3000, "Tên người dùng đã được sử dụng"),
    UNAUTHORIZED(9993, "Lỗi xác thực"),
    ERROR_SYSTEM(193131,"Đã xảy ra lỗi trong quá trình xử lý")
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
