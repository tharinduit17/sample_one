package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class InsuranceProductResponse {
    private String id;
    private String proCode;
    private String proName;
    private String proDescription;
    private Status status;
    private IntermediaryResponse intermediary;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
    private String inactiveReason;
}
