package lk.coop.repository.authentication;

import lk.coop.entity.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);

    @Query("From Role b where b.id=:id")
    Role getOne( @Param("id")  String id);

//    List<Role> findByStatusAndIsDeleted(Status status, Deleted isDeleted);
//
//    List<Role> findByIsDeleted(Deleted isDeleted);

}