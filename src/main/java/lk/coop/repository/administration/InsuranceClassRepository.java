package lk.coop.repository.administration;

import lk.coop.entity.administration.InsuranceClass;
import lk.coop.entity.administration.InsuranceType;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceClassRepository extends JpaRepository<InsuranceClass, String> {

    @Query("From InsuranceClass b where b.isDeleted=:isDelete and b.id=:id")
    InsuranceClass getOne(@Param("isDelete") Deleted isDelete, @Param("id") String id);

    List<InsuranceClass> findByIsDeleted(Deleted isDeleted);

    List<InsuranceClass> findByStatusAndIsDeleted(Status status, Deleted isDeleted);

}
