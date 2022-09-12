package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;


@Data
@Valid
public class ConstructionDetailsRequest {
    private String id;
    private List<ConstructionDetailItemRequest> constructionDetailsItems;;
}
