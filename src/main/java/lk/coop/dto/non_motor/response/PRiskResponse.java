package lk.coop.dto.non_motor.response;

import lombok.Value;

import java.util.Set;


@Value
public class PRiskResponse {
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
    private Set<CoverResponse> covers;
}
