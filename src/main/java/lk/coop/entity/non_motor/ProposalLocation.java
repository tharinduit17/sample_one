package lk.coop.entity.non_motor;

import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "bc_pr_location")
public class ProposalLocation extends BaseEntity {
    @Column
    private String proposalNo;

    @Column(name = "location_code", nullable = false)
    private String locationCode;

    @Column(name = "location_des", nullable = false)
    private String locationDescription;

    @Column(name = "location_sum_insu", nullable = false, precision = 12, scale = 2)
    private Double locationSumInsu;

    @Column
    private String classId;

    @Column
    private String productId;

    @Column(name = "premises_address", nullable = false)
    private String premisesAddress;

    @Column(name = "INACTIVE_REASON")
    private String inactiveReason;

    /**
     * MAPPING TABLE
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProposalRisk> risks;
}
