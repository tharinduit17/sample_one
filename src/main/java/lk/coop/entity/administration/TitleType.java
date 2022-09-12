package lk.coop.entity.administration;


import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bc_title")
public class TitleType extends BaseEntity {

    @Column(length =50)
    private String titleType;

    @Column
    private String titleDesc;

    @Column(name = "inactive_reason")
    private String inactiveReason;
}
