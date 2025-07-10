package webthuetro.otp_service.service;

import java.time.Duration;
import java.util.Random;

public interface OtpHandler {

    Random RANDOM = new Random();
    Duration TTL = Duration.ofSeconds(60);
    default String generateOtp() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }

    boolean verify(String target, String inputOtp);
}
