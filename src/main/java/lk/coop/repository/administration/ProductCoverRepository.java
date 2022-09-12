package lk.coop.repository.administration;


import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.ProductCover;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCoverRepository extends JpaRepository<ProductCover, String>, JpaSpecificationExecutor {

    @Query("From ProductCover b where b.id=:id")
    ProductCover getOne(@Param("id") String id);

    @Query("From ProductCover b where b.status=:status And b.isDeleted=:isDeleted")
    List<ProductCover> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted);

//    @Query("From ProductCover b where b.isDeleted=:isDeleted")
    List<ProductCover> findByIsDeleted(Deleted isDeleted);

    List<ProductCover> findByInsuranceProductIdAndStatusAndIsDeleted(String insuranceProductId, Status status, Deleted isDeleted);

//    List<ProductCover> findByInsuranceProductId(Status status, Deleted isDeleted);
}
