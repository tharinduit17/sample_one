package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class ConstructionDetailsItemsUpdateRequest {

    private String itemName;
    private String itemDesc;
//    private ConstructionDetailsRequest constructionDetails;

    /**
     * Update
     */
    private String id;
    private Status status;
    private String inactiveReson;
}
