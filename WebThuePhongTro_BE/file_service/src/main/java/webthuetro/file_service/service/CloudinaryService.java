package webthuetro.file_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webthuetro.file_service.dto.response.ApiResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {


    private final Cloudinary cloudinary;

    public ResponseEntity<ApiResponse<String>> uploadImage(MultipartFile file) throws IOException {
        try{
            Map<String, Object> options = new HashMap<>();
            options.put("folder", "webthuetro/avatar");
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
            String url =  result.get("secure_url").toString();
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .result(url)
                            .build()
            );
        } catch (Exception ex){
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .error("Upload failed: " + ex.getMessage())
                            .build()
            );

        }
    }


    public ResponseEntity<ApiResponse<String>> deleteImageByUrl(String imageUrl) {
        try {
            String afterUpload = imageUrl.substring(imageUrl.indexOf("/upload/") + 8);
            String[] parts = afterUpload.split("/", 2);
            if (parts.length < 2) {
                return ResponseEntity.ok(
                        ApiResponse.<String>builder()
                                .success(false)
                                .time(LocalDateTime.now())
                                .error("Đã có lỗi xảy ra")
                                .build()
                );
            }
            String publicIdWithExtension = parts[1];
            String publicId = publicIdWithExtension.replaceFirst("\\.[a-zA-Z]+$", "");
            Map<?, ?> results = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .result(results.get("result").toString())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .success(false)
                            .time(LocalDateTime.now())
                            .error("Đã có lỗi xảy ra")
                            .build()
            );
        }
    }

}