package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Data
@Valid
public class BankBranchRequest {
    @NotBlank
    private  String id;
    @NotBlank
    private String name;
}
