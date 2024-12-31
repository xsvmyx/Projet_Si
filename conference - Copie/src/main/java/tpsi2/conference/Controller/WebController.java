package tpsi2.conference.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String publicPage(){
        return "Hello World";
    }

    @GetMapping("/Private")
    public String privatePage(Authentication authentication){
        return "Hello "+authentication.getName();
    }


}
