package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class InsuranceTypeResponse {
    private String id;
    private String inType;
    private String inDesc;
    private Status status;
    private  String inactiveReason;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
    private IntermediaryResponse intermediary;
}
