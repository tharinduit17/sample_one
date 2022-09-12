package lk.coop.dto.authentication.request;

import lk.coop.enums.Login;
import lombok.Data;

@Data
public class LoginSaveRequest {
    private String ipAddress;
    private String userName;
    private Login loginType;
}
