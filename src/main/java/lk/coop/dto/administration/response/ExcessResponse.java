package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class ExcessResponse {
    private String id;
    private Integer year;
    private Double excess;
    private String exDesc;
    private IntermediaryResponse intermediary;
    private ExcessTypeResponse excessType;
    private Status status;
    private String inactiveReason;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
