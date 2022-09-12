package lk.coop.repository.administration;

import lk.coop.entity.administration.InsuranceType;
import lk.coop.entity.administration.TitleType;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TitleTypeRepository extends JpaRepository<TitleType, String> {
    @Query("From TitleType b where b.id=:id")
    TitleType getOne(@Param("id") String id);

    @Query("From TitleType b where b.status=:status And b.isDeleted=:isDeleted")
    List<TitleType> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted);

    @Query("From TitleType b where b.isDeleted=:isDeleted")
    List<TitleType> getAllList(@Param("isDeleted") Deleted isDeleted);

}
