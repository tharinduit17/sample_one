package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Data
@Valid
public class TitleRequest {
    @NotBlank
    private  String id;
}
