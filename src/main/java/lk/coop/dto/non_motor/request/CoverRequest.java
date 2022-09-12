package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CoverRequest {
    @NotNull
    private  String id;
}
