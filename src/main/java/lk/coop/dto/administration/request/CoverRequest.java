package lk.coop.dto.administration.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class CoverRequest {
    @NotNull
    private  String id;
}
