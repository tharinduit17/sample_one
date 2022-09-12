package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="bc_period_cover")
public class PeriodCover extends BaseEntity {

    @Column(length=4)
    private Integer year;

    @Column(length = 100)
    private  String description ;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;
   }
