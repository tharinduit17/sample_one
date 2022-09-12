package lk.coop.dto.administration.request;

import lk.coop.enums.IsDefoult;
import lk.coop.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class ProductCoverSaveRequest {

    private Integer pc_code;
    private String pc_description;
    private Double pc_rate;
    private Double pc_excess;
    private Double pc_excess_amount;
    private String pc_tax_cover;
    private Integer pc_cal_seq;
    private Double pc_event_limit;
    private IsDefoult isDefoult;
    private InsuranceProductRequest insuranceProduct;
    private CoverRequest insuranceCover;

}
