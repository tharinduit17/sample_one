package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class ExcessTypeResponse {

    private String id;
    private String exType;
    private String exDesc;
    private Status status;
    private String inactiveReason;
    private IntermediaryResponse intermediary;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
