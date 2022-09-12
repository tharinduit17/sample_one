package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Valid
@Data
public class InsuranceClassUpdateRequest {
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    private String description;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
