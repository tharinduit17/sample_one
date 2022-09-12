package lk.coop.dto.administration.response;

import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class CoverResponse {
    private String id;
    private Integer co_code;
    private String co_description;
    private Status status;
    private String inactiveReason;
    private Deleted deleted;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;

}
