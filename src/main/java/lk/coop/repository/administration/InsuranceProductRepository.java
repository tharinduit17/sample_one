package lk.coop.repository.administration;


import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, String> {

    @Query("From InsuranceProduct b where b.id=:id And b.intermediary.id=:iid And b.isDeleted=:isDeleted ")
    InsuranceProduct getOne(@Param("id") String id, @Param("iid") String iid, @Param("isDeleted") Deleted isDeleted);

    @Query("From InsuranceProduct b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<InsuranceProduct> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                   @Param("iid") String iid);

    @Query("From InsuranceProduct b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<InsuranceProduct> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);

//    InsuranceProduct getOne(Deleted no, String id);
}
