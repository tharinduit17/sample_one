package lk.coop.repository.administration;

import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.ExcessType;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcessTypeRepository extends JpaRepository<ExcessType, String> {


    @Query("From ExcessType b where b.id=:id And b.intermediary.id=:iid")
    ExcessType getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From ExcessType b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<ExcessType> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                   @Param("iid") String iid);

    @Query("From ExcessType b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<ExcessType> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);
}
