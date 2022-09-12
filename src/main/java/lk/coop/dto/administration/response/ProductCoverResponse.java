package lk.coop.dto.administration.response;

import lk.coop.dto.administration.request.CoverRequest;
import lk.coop.dto.administration.request.InsuranceProductRequest;
import lk.coop.enums.Deleted;
import lk.coop.enums.IsDefoult;
import lk.coop.enums.Status;
import lombok.Data;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Data
public class ProductCoverResponse {

    private String id;
    private Integer pc_code;
    private String pc_description;
    private Double pc_rate;
    private Double pc_excess;
    private Double pc_excess_amount;
    private String pc_tax_cover;
    private Integer pc_cal_seq;
    private Double pc_event_limit;
    private IsDefoult isDefoult;
    private InsuranceProductResponse insuranceProduct;
    private CoverResponse insuranceCover;
    private Status status;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
    private Deleted deleted;
    private String inactiveReason;

}
