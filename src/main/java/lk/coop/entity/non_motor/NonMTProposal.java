package lk.coop.entity.non_motor;

import lk.coop.entity.BaseEntity;
import lk.coop.entity.administration.*;
import lk.coop.enums.ConstructionType;
import lk.coop.enums.IsPolicy;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "bc_non_mt_proposal")
public class NonMTProposal extends BaseEntity {

    @Column(name = "qt_no", length = 15,nullable = false)
    private String quotationNo;

    @Column(name = "pr_no", length = 15,nullable = false)
    private String proposalNo;

    @Column
    private String policyNo;

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

    @Column(name = "insu_nic",nullable = false)
    private String nic;

    @Column(name = "postal_address",nullable = false)
    private String postalAddress;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Column(name = "loan_no",nullable = false)
    private String loanNo;

    @Column(name = "land_name",nullable = false)
    private String landName;

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

    @Column(name = "is_policy", nullable = false)
    @ColumnDefault("0")
    private IsPolicy isPolicy;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    /** MAPPING TABLE*/
    @ManyToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;

    @ManyToMany
    private List<ConstructionDetailsItems> constructionDetailsItems;

    @ManyToOne
    @JoinColumn(name = "title_id", referencedColumnName = "id")
    private TitleType title;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BankBranch bankBranch;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private InsuranceProduct product;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProposalLocation> riskLocations;

    @OneToMany(cascade = CascadeType.ALL)
    private List<NonMTProposalDocument> documents;

    @Column(name = "ISSUED_DATE")
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
}
