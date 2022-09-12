package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class RateSaveRequest {
    private Integer year;
    private Double rate;
    private String rateDesc;
    private InsuranceProductRequest product;
}
