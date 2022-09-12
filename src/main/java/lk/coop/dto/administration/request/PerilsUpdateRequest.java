package lk.coop.dto.administration.request;

import lk.coop.entity.administration.InsuranceType;
import lk.coop.enums.Status;
import lombok.Data;

@Data
public class PerilsUpdateRequest {
    private Integer code;
    private String pName;
    private String pDsesc;
    private IntermediaryRequest intermediary;
    private InsuranceTypeRequest insurance;
    private InsuranceProductRequest insuranceProduct;
    //UPDATE
    private String id;
    private Status status;
    private String inactiveReason;
}
