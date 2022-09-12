package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class ConstructionDetailsItemsResponse {

    private String id;
    private String itemName ;
    private String itemDesc;
    private Status status;
    private String inactiveReason;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
