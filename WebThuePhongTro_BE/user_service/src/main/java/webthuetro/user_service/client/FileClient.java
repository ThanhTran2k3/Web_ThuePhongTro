package webthuetro.user_service.client;


import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import webthuetro.user_service.dto.response.FileClientResponse;

import java.io.IOException;

@Service
public class FileClient {

    private final WebClient webClient;
    private Environment env;

    public FileClient(WebClient.Builder builder, Environment env) {
        String userApi = env.getProperty("HOST_NAME");
        this.webClient = builder.baseUrl(userApi+"/api/image").build();
    }

    public FileClientResponse upload(MultipartFile multipartFile) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        }).contentType(MediaType.APPLICATION_OCTET_STREAM);

        MultiValueMap<String, HttpEntity<?>> multipartData = builder.build();

        return webClient.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData))
                .exchangeToMono(response ->
                        response.bodyToMono(FileClientResponse.class)
                )
                .block();
    }

    public FileClientResponse delete(String imageUrl) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/delete")
                        .queryParam("imageUrl", imageUrl)
                        .build())
                .exchangeToMono(response ->
                        response.bodyToMono(FileClientResponse.class)
                )
                .block();
    }
}
