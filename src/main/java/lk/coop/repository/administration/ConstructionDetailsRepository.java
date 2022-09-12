package lk.coop.repository.administration;

import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.ConstructionDetails;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConstructionDetailsRepository extends JpaRepository<ConstructionDetails, String> {

//    @Query("From ConstructionDetails b where b.id=:id And b.constructionDetailsItems.id=:iid")
//    ConstructionDetails getOne(@Param("id") String id, @Param("iid") String iid);

//    @Query("From ConstructionDetails b where b.status=:status And b.isDeleted=:isDeleted And b.constructionDetailsItems.id=:iid")
//    List<ConstructionDetails> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
//                                   @Param("iid") String iid);

  // @Query("From ConstructionDetails b where b.isDeleted=:isDeleted And b.constructionDetailsItems.id=:iid")
    List<ConstructionDetails> findByIsDeleted(Deleted isDeleted);

    List<ConstructionDetails> findByStatusAndIsDeleted(Status status, Deleted isDeleted);

}
