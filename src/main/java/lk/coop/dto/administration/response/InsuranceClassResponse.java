package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class InsuranceClassResponse {
    private String id;
    private String code;
    private String name;
    private String description;
    private Status status;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
