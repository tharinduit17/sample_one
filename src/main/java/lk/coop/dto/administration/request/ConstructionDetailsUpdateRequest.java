package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class ConstructionDetailsUpdateRequest {
    private String typeName;
    private String typeDescription;
    private List<ConstructionDetailsItemsUpdateRequest> constructionDetailsItems;
//    private IntermediaryRequest intermediary;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
