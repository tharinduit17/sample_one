package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class ExcessSaveRequest {
    private Integer year;
    private Double excess;
    private String exDesc;
    private IntermediaryRequest intermediary;
    private ExcessTypeRequest excessType;
}
