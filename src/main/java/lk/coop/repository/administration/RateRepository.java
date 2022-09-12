package lk.coop.repository.administration;

import lk.coop.entity.administration.Rate;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {
    @Query("From Rate b where b.id=:id")
    Rate getOne(@Param("id") String id);

    @Query("From Rate b where b.status=:status And b.isDeleted=:isDeleted And b.product.id=:iid")
    List<Rate> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                               @Param("iid") String iid);

    @Query("From Rate b where b.isDeleted=:isDeleted And b.product.id=:iid")
    List<Rate> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);

    @Query("From Rate b where b.status=:status And b.product.id=:iid And b.year BETWEEN :from AND :to")
    List<Rate> getRateList(@Param("status") Status status,@Param("iid") String iid,@Param("from") Integer from,@Param("to") Integer to);

    @Query("From Rate b where b.status=:status And b.product.id=:iid")
    Rate getCfRate(@Param("status") Status status,@Param("iid") String iid);

}
