package lk.coop.dto.authentication.request;

import lombok.Data; import lk.coop.enums.*;

import javax.validation.Valid;


@Data
@Valid
public class LoginUserRequest {
    private String username;
    private String password;
    private LoginSaveRequest loginSaveRequest;
}
