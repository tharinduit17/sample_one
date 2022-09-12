package lk.coop.service.authentication.impl;

import lk.coop.converters.DateFormatConverter;
import lk.coop.dto.authentication.request.LoginSaveRequest;
import lk.coop.dto.authentication.response.LoginResponse;
import lk.coop.dto.authentication.response.RoleResponse;
import lk.coop.dto.authentication.response.UserResponse;
import lk.coop.dto.non_motor.response.BankBranchSumResponse;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.authentication.LoginLogger;
import lk.coop.entity.authentication.Role;
import lk.coop.entity.authentication.User;
import lk.coop.repository.authentication.LoginLoggerRepository;
import lk.coop.repository.authentication.UserRepository;
import lk.coop.service.authentication.LoginLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginLoggerServiceImpl implements LoginLoggerService {

    @Autowired
    LoginLoggerRepository loginLogger;

    @Autowired
    UserRepository userRepository;

    @Override
    public LoginResponse save(LoginSaveRequest saveRequest) {
        User user = convertUserSave(saveRequest.getUserName());
        if (user == null) {
            return null;
        } else {
            LoginLogger save = this.loginLogger.save(convert(saveRequest));
            return convertLogin(save);
        }
    }


    @Override
    public LoginResponse getById(String id) {
        try {
            return convertLogin(this.loginLogger.getById(id));
        } catch (Exception e) {
            log.error("Error getById LoginLogger {} ", id);
            return null;
        }

    }

    @Override
    public List<LoginResponse> getByUserName(String userName) {
        try {
            return this.loginLogger.findByUserUsername(userName).stream().map(LoginLoggerServiceImpl::convertLogin).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error FindBy UserName LoginLogger {} ", userName);
            return null;
        }
    }


    //SAVE
    private LoginLogger convert(LoginSaveRequest loginSaveRequest) {
        LoginLogger loginLogger = new LoginLogger();
        try {
            loginLogger.setIpAddress(loginSaveRequest.getIpAddress());
            loginLogger.setUser(convertUserSave(loginSaveRequest.getUserName()));
            loginLogger.setLoginType(loginSaveRequest.getLoginType());
        } catch (Exception e) {
            log.error("LoginLogger Save Convert {} ", loginSaveRequest.getUserName());
            return null;
        }
        return loginLogger;
    }

    //SAVE USER
    private User convertUserSave(String userName) {
        try {
            User user = this.userRepository.findByUsername(userName);
            if (user == null) {
                throw new UsernameNotFoundException("Invalid username or password.");
            }
            return user;
        } catch (UsernameNotFoundException e) {
            log.error("User Name NotFound {} ", userName);
            return null;
        }
    }

    //LOGIN RESPONSE CONVERT
    private static LoginResponse convertLogin(LoginLogger loginLogger) {
        DateFormatConverter df = new DateFormatConverter();
        if (loginLogger == null) {
            return null;
        }

        return new LoginResponse(loginLogger.getId(), loginLogger.getIpAddress(), convertUser(loginLogger.getUser()), loginLogger.getLoginType(),
                df.patternDateTime(loginLogger.getCreatedDateTime()));
    }

    //USER RESPONSE CONVERT
    private static UserResponse convertUser(User user) {
        DateFormatConverter df = new DateFormatConverter();
        if (user == null) {
            return null;
        }

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getName(),
                convertRole(user.getRoles()),convertBranchResponse(user.getBankBranch()));
    }

    //ROLE RESPONSE CONVERT
    private static List<RoleResponse> convertRole(List<Role> roles) {
        List<RoleResponse> roleResList = new ArrayList<>();
        for (Role role : roles) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            roleResponse.setDescription(role.getDescription());
            roleResList.add(roleResponse);
        }

        return roleResList;
    }

    /**BANK BRANCH RESPONSE RESPONSE CONVERT*/
    private static BankBranchSumResponse convertBranchResponse(BankBranch bankBranch) {
        if (bankBranch == null) {
            return null;
        }

        return new BankBranchSumResponse(bankBranch.getId(), bankBranch.getCode(),bankBranch.getName());
    }


}
