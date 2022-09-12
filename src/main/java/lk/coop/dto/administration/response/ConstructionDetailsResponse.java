package lk.coop.dto.administration.response;

import lk.coop.dto.administration.request.ConstructionDetailsItemsRequest;
import lk.coop.dto.administration.request.ConstructionDetailsRequest;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class ConstructionDetailsResponse {

    private String id;

    private String typeName ;

    private String typeDescription;

    private Status status;

    private List<ConstructionDetailsItemsResponse> constructionDetailsItems;

    private String createdBy;

    private Date createdDateTime;

    private String modifiedBy;

    private Date modifiedDateTime;

    private String inactiveReason;

}
