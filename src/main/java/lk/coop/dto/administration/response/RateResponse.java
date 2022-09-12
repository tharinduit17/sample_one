package lk.coop.dto.administration.response;

import lk.coop.dto.non_motor.response.ProductResponse;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class RateResponse {
    private String id;
    private Integer year;
    private Double rate;
    private String rateDesc;
    private ProductResponse product;
    private Status status;
    private String inactiveReason;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
