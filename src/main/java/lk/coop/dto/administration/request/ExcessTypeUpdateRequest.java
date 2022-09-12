package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class ExcessTypeUpdateRequest {

    private String exType;
    private String exDesc;
    public IntermediaryRequest intermediary;
    /**
     * UPDATE
     */
    private String id;
    private Status status;
    private String inactiveReson;
}
