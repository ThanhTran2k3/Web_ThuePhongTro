package webthuetro.otp_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webthuetro.otp_service.dto.request.VerifyRequest;
import webthuetro.otp_service.dto.response.ApiResponse;
import webthuetro.otp_service.service.EmailService;

@Controller
@RequestMapping("/api/otp")
@AllArgsConstructor
public class OTPController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<String>> sendOTPEmail(@RequestParam String email){
        return emailService.sendOTP(email);
    }

    @PostMapping("/verify/email")
    public ResponseEntity<ApiResponse<String>> verifyOTPEmail(@RequestBody VerifyRequest verifyRequest){
        return emailService.verifyOTP(verifyRequest);
    }
}
