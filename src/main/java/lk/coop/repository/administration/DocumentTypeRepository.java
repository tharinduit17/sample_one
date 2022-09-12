package lk.coop.repository.administration;

import lk.coop.entity.administration.DocumentType;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType ,String> {

    @Query("From DocumentType b where b.id=:id And b.intermediary.id=:iid")
    DocumentType getOne(@Param("id") String id, @Param("iid") String iid);

    @Query("From DocumentType b where b.status=:status And b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<DocumentType> getActiveList(@Param("status") Status status, @Param("isDeleted") Deleted isDeleted,
                                   @Param("iid") String iid);

    @Query("From DocumentType b where b.isDeleted=:isDeleted And b.intermediary.id=:iid")
    List<DocumentType> getAllList(@Param("isDeleted") Deleted isDeleted,@Param("iid") String iid);
}
