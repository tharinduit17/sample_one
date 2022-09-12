package lk.coop.repository.administration;

import lk.coop.entity.administration.Excess;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcessRepository extends JpaRepository<Excess, String> {

    @Query("From Excess b where b.id=:id And b.intermediary.id=:iid")
    Excess getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From Excess b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<Excess> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                               @Param("iid") String iid);

    @Query("From Excess b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<Excess> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);
}
