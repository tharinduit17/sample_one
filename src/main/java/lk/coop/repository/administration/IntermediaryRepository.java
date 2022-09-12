package lk.coop.repository.administration;


import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntermediaryRepository extends JpaRepository<Intermediary, String> {

    @Query("From Intermediary b where b.id=:id")
    Intermediary getOne(@Param("id") String id);

    @Query("From Intermediary b where b.status=:status And b.isDeleted=:isDeleted")
    List<Intermediary> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted);

    @Query("From Intermediary b where b.isDeleted=:isDeleted")
    List<Intermediary> getAllList(@Param("isDeleted") Deleted isDeleted);
}
