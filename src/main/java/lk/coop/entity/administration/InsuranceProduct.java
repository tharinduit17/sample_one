package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_insurance_product")
public class InsuranceProduct extends BaseEntity {

    @Column(name = "insu_product_code",length = 4)
    private String proCode;

    @Column(name = "insu_product_name",length =100)
    private String proName;

    @Column(name = "insu_product_des")
    private String proDescription;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @ManyToOne
    @JoinColumn(name="class_id",referencedColumnName = "id")
    private InsuranceClass insuranceClass;

    @OneToOne
    @JoinColumn(name="Intermediary_id",referencedColumnName = "id",nullable=false)
    private Intermediary intermediary;
}
