package webthuetro.file_service.config;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

    private Environment env;

    @Bean
    public Cloudinary cloudinary() {
        String cloudName = env.getProperty("CLOUD_NAME");
        String apiKey = env.getProperty("CLOUD_API_KEY");
        String secretKey = env.getProperty("CLOUD_API_SECRET");
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", secretKey);
        config.put("secure", "true");
        return new Cloudinary(config);
    }
}