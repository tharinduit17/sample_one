package lk.coop.repository.administration;

import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Perils;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerilsRepository extends JpaRepository<Perils, String> {

    @Query("From Perils b where b.id=:id And b.intermediary.id=:iid")
    Perils getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From Perils b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<Perils> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                   @Param("iid") String iid);

    @Query("From Perils b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<Perils> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);

}
