package lk.coop.repository.administration;


import lk.coop.entity.administration.BankBranch;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, String> {

    @Query("From BankBranch b where b.id=:id And b.intermediary.id=:iid")
    BankBranch getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From BankBranch b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<BankBranch> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                   @Param("iid") String iid);

    @Query("From BankBranch b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<BankBranch> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);

}
