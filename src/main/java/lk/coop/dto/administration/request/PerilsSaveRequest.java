package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class PerilsSaveRequest {
    private Integer code;
    private String pName;
    private String  pDsesc;
    private IntermediaryRequest intermediary;
    private InsuranceTypeRequest insurance;
    private InsuranceProductRequest insuranceProduct;
}

