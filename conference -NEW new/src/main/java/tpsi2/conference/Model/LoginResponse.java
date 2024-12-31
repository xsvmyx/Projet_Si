package tpsi2.conference.Model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
}
