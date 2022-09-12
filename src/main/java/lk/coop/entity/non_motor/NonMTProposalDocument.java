package lk.coop.entity.non_motor;

import lk.coop.entity.BaseEntity;
import lk.coop.entity.administration.*;
import lk.coop.enums.ConstructionType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "bc_mt_proposal_document")
public class NonMTProposalDocument extends BaseEntity {

    @Column(nullable = false)
    private String proposalNo;

    @Column
    private String imagePath;

    @Column
    private String imageNo;

    @ManyToOne
    @JoinColumn(name = "documentType_id", referencedColumnName = "id", nullable = false)
    private DocumentType documentType;

}
