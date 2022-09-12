package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_excess")
public class Excess extends BaseEntity {

    @Column(length = 4)
    private Integer year;

    @Column(precision = 5, scale = 2)
    private Double excess;

    @Column
    private String exDesc;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;

    @ManyToOne
    @JoinColumn(name = "excessType_id", referencedColumnName = "id", nullable = false)
    private ExcessType excessType;

}
