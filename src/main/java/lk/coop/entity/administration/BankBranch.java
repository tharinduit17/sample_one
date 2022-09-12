package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_bank_branch")
public class BankBranch extends BaseEntity {

    @Column(length = 4)
    private String code;

    @Column(length =100)
    private String name;

    @Column(length =3)
    private String grade;

    @Column(length =4)
    private String province;

    @Column(unique = true)
    private String email;

    @Column(length =50)
    private String area;

    @Column(length =50)
    private String adminDistrict;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;
}
