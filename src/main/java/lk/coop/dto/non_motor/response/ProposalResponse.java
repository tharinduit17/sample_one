package lk.coop.dto.non_motor.response;

import lk.coop.enums.ConstructionType;
import lk.coop.enums.IsPolicy;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class ProposalResponse {
    private String id;
    private String quotationNo;
    private String proposalNo;
    private String policyNo;
    private String intermediaryCode;
    private String intermediaryBranchCode;
    private String intermediaryProductCode;
    private Integer intermediaryYearCode;
    private Integer seqNo;
    private String classId;
    private String insuredName;
    private String nic;
    private String postalAddress;
    private Date fromDate;
    private Date toDate;
    private String loanNo;
    private String landName;
    private Double netInsured;
    private Double sumInsured;
    private ConstructionType constructionType;
    private Integer coverPeriod;
    private String premisesAddress;
    private Double totalPremium;
    private IsPolicy isPolicy;
    private IntermediaryResponse intermediary;
    private List<ConstructionItemsResponse> constructionDetailsItems;
    private BankBranchSumResponse bankBranch;
    private ProductSumResponse product;
    private List<PLocationSumResponse> riskLocations;
    private List<PDocumentResponse> documents;
}
