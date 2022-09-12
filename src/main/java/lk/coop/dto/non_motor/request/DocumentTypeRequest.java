package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Data
@Valid
public class DocumentTypeRequest {
    @NotBlank
    private  String documentTypeId;
    private  String imageNo;
}
