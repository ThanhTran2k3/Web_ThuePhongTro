package webthuetro.otp_service;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OtpServiceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		for (DotenvEntry entry : dotenv.entries()) {
			System.setProperty(entry.getKey(), entry.getValue());
		}
		SpringApplication.run(OtpServiceApplication.class, args);
	}

}
