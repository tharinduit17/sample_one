package lk.coop.dto.non_motor.response;

import lk.coop.enums.ConstructionType;
import lombok.Value;

import java.util.Set;

@Value
public class QuotationResponse {
    private String id;
    private String classId;
    private String quotationNo;
//    private String intermediaryProductCode;
    private Integer intermediaryYearCode;
    private String insuredName;
    private Double netInsured;
    private ConstructionType constructionType;
    private Integer coverPeriod;
    private String premisesAddress;
    private Integer version;
    /**
     * MAPPING DATA
     */
    private BankBranchSumResponse bankBranch;
//    private InsuranceTypeSumResponse insuranceType;
    private ProductSumResponse product;
    private Set<LocationSumResponse> location;
//    private Boolean srcc;
//    private Boolean tc;
    private Double totalPremium;
}
