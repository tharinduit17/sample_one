package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class ExcessTypeSaveRequest {

    private String exType;
    private String exDesc;
    private IntermediaryRequest intermediary;


}
