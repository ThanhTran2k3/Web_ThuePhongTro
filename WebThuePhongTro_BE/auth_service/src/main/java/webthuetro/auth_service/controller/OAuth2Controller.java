package webthuetro.auth_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webthuetro.auth_service.service.OAuth2Service;

@RestController
@RequestMapping("/api/oauth2")
@AllArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;


    @GetMapping("/google")
    public ResponseEntity<Void> googleAuth(@RequestParam("code") String code) throws Exception {
        return oAuth2Service.googleCallback(code);
    }
}
