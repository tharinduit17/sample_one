package lk.coop.dto.administration.response;

import lombok.Value;

@Value
public class RateSummaryResponse {
    private String id;
    private Integer year;
    private Double rate;
}
