package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bc_intermediary")
public class Intermediary extends BaseEntity {

    @Column(name = "int_name", length = 50)
    private String name;

    @Column(name = "int_des")
    private String description;

    @Column
    private String email;

    @Column(name = "INACTIVE_REASON", length = 100)
    private String inactiveReason;
}
