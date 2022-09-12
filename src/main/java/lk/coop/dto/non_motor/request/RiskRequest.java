package lk.coop.dto.non_motor.request;

import lombok.Data;

import java.util.Set;


@Data
public class RiskRequest {
    private String id;
    private String riskCode;
    private String riskDesc;
    private Double riskSum;
    private Double riskRate;
//    private Double basicPremium;
//    private Double excessAmount;
//    private Double minLimit;
//    private Double excess;
//    private Double eventLimit;
    private Set<CoverRequest> covers;
}
