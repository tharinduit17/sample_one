package lk.coop.dto.administration.request;

import lk.coop.enums.Status;
import lombok.Data;

import javax.validation.Valid;


@Data
@Valid
public class IntermediaryUpdateRequest {
    private String name;
    private String description;
    private String email;
    //UPDATE
    private String id;
    private Status status;
    private String inactiveReason;
}
