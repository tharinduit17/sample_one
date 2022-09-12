package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class ExcessUpdateRequest {
    private Integer year;
    private Double excess;
    private String exDesc;
    private IntermediaryRequest intermediary;
    private ExcessTypeRequest excessType;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
