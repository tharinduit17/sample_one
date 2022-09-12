package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_con_det_item")
public class ConstructionDetailsItems extends BaseEntity {

    @Column(length=100)
    private String itemName;

    @Column(length = 100)
    private  String itemDesc ;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    @ManyToOne
    @JoinColumn(name = "constructionDetails_id", referencedColumnName = "id", nullable = false)
    private ConstructionDetails constructionDetail;

}




