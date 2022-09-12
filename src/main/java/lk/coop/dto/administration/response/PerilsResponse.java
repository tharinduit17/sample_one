package lk.coop.dto.administration.response;

import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;
@Value
public class PerilsResponse {

    private String id;
    private Integer code;
    private String pName;
    private String  pDsesc;
    private Status status;
    private String inactiveReason;
    private IntermediaryResponse intermediary;
    private InsuranceTypeResponse insurance;
    private InsuranceProductResponse insuranceProduct;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;

}
