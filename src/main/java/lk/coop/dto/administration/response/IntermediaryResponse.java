package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;


@Value
public class IntermediaryResponse {
    private String id;
    private String name;
    private String description;
    private String email;
    private Status status;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
