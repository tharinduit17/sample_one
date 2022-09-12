package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;


@Data
@Valid
public class QuotationSaveRequest {
    private String name;
    private String description;
    private String email;
}
