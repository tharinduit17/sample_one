package lk.coop.repository.authentication;

import lk.coop.entity.authentication.LoginLogger;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoginLoggerRepository extends CrudRepository<LoginLogger,  String> {

    LoginLogger getById(String id);
    List<LoginLogger> findByUserUsername(String userName);
}