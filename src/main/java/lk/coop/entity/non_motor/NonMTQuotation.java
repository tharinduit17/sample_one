package lk.coop.entity.non_motor;

import lk.coop.entity.BaseEntity;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.ConstructionType;
import lk.coop.enums.IsPolicy;
import lk.coop.enums.IsProposal;
import lk.coop.enums.Status;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "bc_non_mt_quotation")
public class NonMTQuotation extends BaseEntity {

    @Column(name = "qt_no", length = 15,nullable = false)
    private String quotationNo;

    @Column(name = "int_code",length = 5,nullable = false)
    private String intermediaryCode;

    @Column(name = "int_bra_code",length = 5,nullable = false)
    private String intermediaryBranchCode;

    @Column(name = "int_pro_code",length = 5,nullable = false)
    private String intermediaryProductCode;

    @Column(name = "int_yer_code",length = 2,nullable = false)
    private Integer intermediaryYearCode;

    @Column(name = "int_seq_no",length = 4,nullable = false)
    private Integer seqNo;

    @Column
    private String classId;

    @Column(name = "insu_name",nullable = false)
    private String insuredName;

    @Column(name = "net_insured",nullable = false,precision=12, scale=2)
    private Double netInsured;

    @Column(name = "sum_insured",nullable = false,precision=12, scale=2)
    private Double sumInsured;

    @Column(name = "construction_type",nullable = false)
    private ConstructionType constructionType;

    @Column(name = "cover_period")
    private Integer coverPeriod;

    @Column(name = "premises_address",nullable = false)
    private String premisesAddress;

    @Column(name = "premium",nullable = false,precision=12, scale=2)
    private Double totalPremium;

    @Column(name = "version",nullable = false)
    private Integer version;

    @Column(name = "is_proposal", nullable = false)
    @ColumnDefault("0")
    private IsProposal isProposal;

    @Column(name = "is_policy", nullable = false)
    @ColumnDefault("0")
    private IsPolicy isPolicy;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    /** MAPPING TABLE*/
    @ManyToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BankBranch bankBranch;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private InsuranceProduct product;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RiskLocation> riskLocations;
}
