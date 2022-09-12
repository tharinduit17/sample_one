package lk.coop.repository.administration;

import lk.coop.entity.administration.ConstructionDetailsItems;
import lk.coop.entity.administration.InsuranceClass;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConstructionDetailsItemsRepository extends JpaRepository<ConstructionDetailsItems,String > {
//
//    @Query("From ConstructionDetailsItems b where b.id=:id And b.intermediary.id=:iid")
//    ConstructionDetailsItems getOne(@Param("id") String id, @Param("iid") String iid);
//
//    @Query("From ConstructionDetailsItems b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
//    List<ConstructionDetailsItems> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
//                                     @Param("iid") String iid);
//
//    @Query("From ConstructionDetailsItems b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
//    List<ConstructionDetailsItems> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);

    @Query("From ConstructionDetailsItems b where b.isDeleted=:isDelete and b.id=:id")
    ConstructionDetailsItems getOne(@Param("isDelete") Deleted isDelete, @Param("id") String id);

    List<ConstructionDetailsItems> findByIsDeleted(Deleted isDeleted);

    List<ConstructionDetailsItems> findByStatusAndIsDeleted(Status status, Deleted isDeleted);
}
