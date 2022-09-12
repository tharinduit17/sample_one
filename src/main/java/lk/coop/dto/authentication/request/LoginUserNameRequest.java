package lk.coop.dto.authentication.request;

import lombok.Data;

import javax.validation.Valid;


@Data
public class LoginUserNameRequest {
    private String userName;
}
