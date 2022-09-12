package lk.coop.entity.non_motor;

import lk.coop.entity.BaseEntity;
import lk.coop.entity.administration.Cover;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "bc_pr_risk")
public class ProposalRisk extends BaseEntity {

    @Column(name = "qt_no", length = 15,nullable = false)
    private String proposalNo;

    @Column
    private String classId;

    @Column
    private String productId;

    @Column(name = "risk_code",length = 5,nullable = false)
    private String riskCode;

    @Column(name = "risk_desc")
    private String riskDesc;

    @Column(name = "risk_sum",precision=12, scale=2)
    private Double riskSum;

    @Column(name = "risk_rate",precision=12, scale=2)
    private Double riskRate;

    @Column(name = "basic_premium",precision=12, scale=2)
    private Double basicPremium;

    @Column(name = "excess_amount",precision=12, scale=2)
    private Double excessAmount;

    @Column(name = "min_limit",precision=12, scale=2)
    private Double minLimit;

    @Column(name = "excess",precision=12, scale=2)
    private Double excess;

    @Column(name = "event_limit",precision=12, scale=2)
    private Double eventLimit;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    /** MAPPING TABLE*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bc_pr_risk_cover",
            joinColumns = {
                    @JoinColumn(name = "RISK_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "COVER_ID")})
    private List<Cover> covers;
}
