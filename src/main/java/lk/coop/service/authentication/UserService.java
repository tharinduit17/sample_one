package lk.coop.service.authentication;

import lk.coop.dto.authentication.request.UserRequest;
import lk.coop.dto.authentication.response.UserResponse;
import lk.coop.entity.authentication.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse save(UserRequest user);

    List<User> findAll();

    UserResponse findOne(String username);

    User findByUserName(String userName);
}
