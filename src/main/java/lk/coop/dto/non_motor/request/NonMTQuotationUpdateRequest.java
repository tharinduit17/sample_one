package lk.coop.dto.non_motor.request;

import lk.coop.enums.ConstructionType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Data
@Valid
public class NonMTQuotationUpdateRequest {
    private String id;
    private String classId;
    //    private String intermediaryProductCode;
    private Integer intermediaryYearCode;
    @NotEmpty
    private String insuredName;
    private Double netInsured;
    //    private Double policyFee;
    private ConstructionType constructionType;
    private Integer coverPeriod;
    private String premisesAddress;
    private Integer version;
    /**
     * MAPPING DATA
     */
//    private TitleRequest title;
    private BankBranchNMTRequest bankBranch;
    //    private InsuranceTypeRequest insuranceType;
//    private PeriodCoverRequest periodCoverRequest;
    private ProductRequest product;
    private Set<LocationRequest> location;
    private Boolean srcc;
    private Boolean tc;
}
