package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class DocumentTypeUpdateRequest {
    private String dcType;
    private String dcDesc;
    private IntermediaryRequest intermediary;
    /**
     * Update
     */
    private String id;
    private Status status;
    private String inactiveReson;
}
