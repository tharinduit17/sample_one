package lk.coop.repository.administration;


import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Cover;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoverRepository extends JpaRepository<Cover, String> {

    @Query("From Cover b where b.id=:id")
    Cover getOne(@Param("id") String id);

    @Query("From Cover b where b.status=:status And b.isDeleted=:isDeleted")
    List<Cover> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted);

    @Query("From Cover b where b.isDeleted=:isDeleted")
    List<Cover> getAllList(@Param("isDeleted") Deleted isDeleted);

}
