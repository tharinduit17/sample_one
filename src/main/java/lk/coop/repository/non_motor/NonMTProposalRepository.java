package lk.coop.repository.non_motor;


import lk.coop.entity.non_motor.NonMTProposal;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NonMTProposalRepository extends JpaRepository<NonMTProposal, String> {

    @Query("From NonMTProposal b where b.id =:id And b.intermediary.id=:iid")
    NonMTProposal getOne(@Param("id") String id, @Param("iid") String iid);

//    @Query("SELECT MAX(b.seqNo) FROM NonMTProposal b where b.intermediaryCode =:intr_code And b.intermediaryBranchCode=:intr_brCode And b.intermediaryProductCode=:intr_proCode And b.intermediaryYearCode=:intr_year")
//    Integer getMaxSeqNo(@Param("intr_code") String intr_code, @Param("intr_brCode") String intr_brCode, @Param("intr_proCode") String intr_proCode, @Param("intr_year") Integer intr_year);

    List<NonMTProposal> findByStatus(Status status);

    @Query("From NonMTProposal b where b.status=:status And b.bankBranch.id=:branchId And b.intermediary.id=:iid")
    List<NonMTProposal> getActiveList(@Param("status") Status status, @Param("branchId") String branchId,@Param("iid") String iid);


//    @Query("From NonMTProposal b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
//    List<NonMTProposal> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
//                                   @Param("iid") String iid);

}
