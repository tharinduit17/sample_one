package lk.coop.dto.administration.request;

import lombok.Data;

@Data
public class PeriodCoverSaveRequest {
    private Integer year;
    private String description;
    private IntermediaryRequest intermediary;
}

