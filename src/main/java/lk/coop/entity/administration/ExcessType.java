package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_excess_type")
public class ExcessType extends BaseEntity {

    @Column(length = 4)
    private  String exType ;

    @Column(length = 50)
    private  String exDesc ;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;
}





