package lk.coop.dto.non_motor.response;

import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
public class PLocationSumResponse {
    private String id;
    private String locationCode;
    private String locationDescription;
    private Double locationSumInsu;
    private String premisesAddress;
    private List<PRiskResponse> risks;
}
