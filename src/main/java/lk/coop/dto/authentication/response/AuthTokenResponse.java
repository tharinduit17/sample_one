package lk.coop.dto.authentication.response;

import lombok.Value;

@Value
public class AuthTokenResponse {

    private UserResponse user;

    private String token;
}
