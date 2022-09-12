package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@Data
@Valid
public class InsuranceProductUpdateRequest {
    @NotEmpty
    private String proCode;
    private String proName;
    private String proDescription;
    @NotEmpty
    private String insuranceClassId;
    @NotEmpty
    public IntermediaryRequest intermediary;
    /**
     * UPDATE
     */
    private String id;
    private Status status;
    private String inactiveReason;
}
