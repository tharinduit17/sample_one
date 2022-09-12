package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class TitleTypeUpdateRequest {
    private String titleType;
    private String titleDesc;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
