package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Data
@Valid
public class ProductRequest {
    @NotBlank
    private  String id;
    @NotBlank
    private String code;
}
