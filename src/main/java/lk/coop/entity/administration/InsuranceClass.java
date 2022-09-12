package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_insurance_class")
public class InsuranceClass extends BaseEntity {

    @Column(name = "insu_class_code",length = 4)
    private String code;

    @Column(name = "insu_class_name",length =100)
    private String name;

    @Column(name = "insu_class_des")
    private String description;

    @Column(name = "inactive_reason")
    private String inactiveReason;


}
