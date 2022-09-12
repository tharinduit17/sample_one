package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bc_cover")
public class Cover extends BaseEntity{
    @Column(length = 5)
    private Integer co_code;

    @Column(length =255)
    private String co_description;

    @Column(name = "inactive_reason")
    private String inactiveReason;
}
