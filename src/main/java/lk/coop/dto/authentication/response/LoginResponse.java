package lk.coop.dto.authentication.response;

import lk.coop.enums.Login;
import lombok.Value;


@Value
public class LoginResponse {
    private String id;
    private String ipAddress;
    private UserResponse user;
    private Login loginType;
    private String createdDate;
}
