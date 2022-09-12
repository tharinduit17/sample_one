package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

@Data
public class PeriodCoverUpdateRequest {
    private Integer year;
    private String description;
    public IntermediaryRequest intermediary;
    /**
     * UPDATE
     */
    private String id;
    private Status status;
    private String inactiveReson;

}
