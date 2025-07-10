package webthuetro.otp_service.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import webthuetro.otp_service.dto.request.VerifyRequest;
import webthuetro.otp_service.dto.response.ApiResponse;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EmailService implements OtpHandler {


    private final JavaMailSender emailSender;

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "otp:email:";

    public ResponseEntity<ApiResponse<String>> sendOTP(String email) {
        try{
            String otp = generateOtp();
            redisTemplate.opsForValue().set(PREFIX + email, otp, TTL);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("OTP xác thực");
            message.setText(buildHtmlEmailContent(otp));
            emailSender.send(message);
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .result("Gửi OTP thành công")
                            .build()
            );
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .time(LocalDateTime.now())
                            .error("Lỗi gửi OTP")
                            .build()
            );
        }

    }

    public ResponseEntity<ApiResponse<String>> verifyOTP(VerifyRequest verifyRequest){
        if(verify(verifyRequest.getEmail(), verifyRequest.getOtp())){
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .result("Xác thực thành công")
                            .build()
            );
        }
        return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                        .success(false)
                        .time(LocalDateTime.now())
                        .error("OTP không đúng")
                        .build()
        );
    }

    private String buildHtmlEmailContent(String otp) {
        return "<html>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }" +
                "h2 { color: #4CAF50; }" +
                ".otp-code { font-size: 24px; font-weight: bold; color: #333; background-color: #e7f7e7; padding: 10px; border-radius: 5px; }" +
                "</style></head>" +
                "<body>" +
                "<h2>Your OTP Code</h2>" +
                "<p>Dear User,</p>" +
                "<p>Thank you for using our service. Your OTP code is:</p>" +
                "<p class='otp-code'>" + otp + "</p>" +
                "<p>If you did not request this, please ignore this email.</p>" +
                "<p>Best regards,<br>Trọ24h</p>" +
                "</body>" +
                "</html>";
    }


    @Override
    public boolean verify(String email, String inputOtp) {
        String key = PREFIX + email;
        String storedOtp = redisTemplate.opsForValue().get(key);
        boolean match = inputOtp != null && inputOtp.equals(storedOtp);

        if (match) {
            redisTemplate.delete(key);
        }

        return match;
    }

}
