package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "bc_document_type")
public class DocumentType extends BaseEntity {


    @Column(length=4)
    private String dcType;

    @Column(length = 100)
    private  String dcDesc ;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;
}


