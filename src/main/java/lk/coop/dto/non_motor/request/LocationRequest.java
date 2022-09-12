package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@Data
@Valid
public class LocationRequest {
    private String id;
    @NotBlank
    private String locationCode;
    private String locationDescription;
    private Double locationSumInsu;
    private String premisesAddress;
    private List<RiskRequest> risks;
}
