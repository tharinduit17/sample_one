package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_insu_type")
public class InsuranceType extends BaseEntity {

    @Column(length =50)
    private String inType;

    @Column
    private String inDesc;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;

}
