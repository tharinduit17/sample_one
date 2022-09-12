package lk.coop.service.authentication;


import lk.coop.dto.authentication.request.LoginSaveRequest;
import lk.coop.dto.authentication.response.LoginResponse;

import java.util.List;

public interface LoginLoggerService {

    LoginResponse save(LoginSaveRequest request);

    LoginResponse getById(String id);

    List<LoginResponse> getByUserName(String UserName);

}
