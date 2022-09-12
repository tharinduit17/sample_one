package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_rate")
public class Rate extends BaseEntity {

    @Column(length = 4)
    private Integer year;

    @Column(precision = 5, scale = 4)
    private Double rate;

    @Column
    private String rateDesc;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private InsuranceProduct product;
}
