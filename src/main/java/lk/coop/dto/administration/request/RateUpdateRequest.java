package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class RateUpdateRequest {
    private Integer year;
    private Double rate;
    private String rateDesc;
    private InsuranceProductRequest product;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
