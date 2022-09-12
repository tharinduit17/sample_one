package lk.coop.dto.administration.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@Data
@Valid
public class InsuranceProductSaveRequest {
    @NotEmpty
    private String proCode;
    private String proName;
    private String proDescription;
    @NotEmpty
    private String insuranceClassId;
    @NotEmpty
    public IntermediaryRequest intermediary;
}
