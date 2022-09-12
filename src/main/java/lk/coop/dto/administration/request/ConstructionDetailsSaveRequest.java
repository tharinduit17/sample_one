package lk.coop.dto.administration.request;

import lombok.Data;

import java.util.List;

@Data
public class ConstructionDetailsSaveRequest {

    private String typeName;

    private String typeDescription;

    private List<ConstructionDetailsItemsSaveRequest> constructionDetailsItems;
}
