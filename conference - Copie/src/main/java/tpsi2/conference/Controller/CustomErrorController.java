package tpsi2.conference.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @GetMapping("error")
    public String handleError(Authentication authentication, HttpServletRequest request) {
        return "Hello "+authentication.getName()+" tu t'es trompé : "+ request.getProtocol();
    }
}
