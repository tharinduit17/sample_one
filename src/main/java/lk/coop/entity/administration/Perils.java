package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_perils")
public class Perils extends BaseEntity {

    @Column(length = 4)
    private Integer code;

    @Column(length = 100)
    private String pName;

    @Column(length = 100)
    private String pDsesc;

    @Column(name = "INACTIVE_REASON", length = 255)
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name="Intermediary_id",referencedColumnName = "id",nullable=false)
    private Intermediary intermediary;

    @ManyToOne
    @JoinColumn(name="insurance_type_id",referencedColumnName = "id",nullable=false)
    private InsuranceType insurance;

    @ManyToOne
    @JoinColumn(name="insurance_product_id",referencedColumnName = "id",nullable=false)
    private InsuranceProduct insuranceProduct;

}
