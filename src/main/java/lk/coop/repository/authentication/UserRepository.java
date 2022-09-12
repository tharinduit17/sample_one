package lk.coop.repository.authentication;

import lk.coop.entity.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository; import lk.coop.enums.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String userName);
}