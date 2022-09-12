package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class DocumentTypeSaveRequest {
    private String dcType;
    private String dcDesc;
    private IntermediaryRequest intermediary;
}
