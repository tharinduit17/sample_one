package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;
@Value
public class PeriodCoverResponse {
    private String id;
    private Integer year;
    private String description;
    private String inactiveReason;
    private IntermediaryResponse intermediary;
    private Status status;
    private  String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
