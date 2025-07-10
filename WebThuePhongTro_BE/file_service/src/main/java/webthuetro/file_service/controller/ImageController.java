package webthuetro.file_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webthuetro.file_service.dto.response.ApiResponse;
import webthuetro.file_service.service.CloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@AllArgsConstructor
public class ImageController {


    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return cloudinaryService.uploadImage(file);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestParam("imageUrl") String url){
        return cloudinaryService.deleteImageByUrl(url);
    }
}