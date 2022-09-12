package lk.coop.dto.administration.response;

import lk.coop.dto.administration.request.ConstructionDetailsRequest;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class DocumentTypeResponse {
    private String id;
    private String dcType ;
    private String dcDesc;
    private Status status;
    private IntermediaryResponse intermediary;
   // private ConstructionDetailsRequest constructionDetails;
    private String inactiveReason;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
