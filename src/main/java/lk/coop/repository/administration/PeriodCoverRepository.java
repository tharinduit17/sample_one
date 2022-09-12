package lk.coop.repository.administration;

import lk.coop.entity.administration.PeriodCover;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeriodCoverRepository extends JpaRepository<PeriodCover, String> {

    @Query("From PeriodCover b where b.id=:id And b.intermediary.id=:iid")
    PeriodCover getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From PeriodCover b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid  order by b.year ASC")
    List<PeriodCover> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                    @Param("iid") String iid);
    @Query("From PeriodCover b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<PeriodCover> getAllList(@Param("isDeleted") Deleted isDeleted, @Param("iid") String iid);
}