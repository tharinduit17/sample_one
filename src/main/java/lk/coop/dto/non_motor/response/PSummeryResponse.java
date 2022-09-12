package lk.coop.dto.non_motor.response;

import lk.coop.enums.IsPolicy;
import lk.coop.enums.IsProposal;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class PSummeryResponse {
    private String id;
    private BankBranchSumResponse branchName;
    private String quotationNo;
    private String policyNo;
    private String insuredName;
    private Double netInsured;
    private Double totalPremium;
    private Date issuedDate;
    private IsPolicy isPolicy;
    private String nic;
    private String postalAddress;
    private Date fromDate;
    private Date toDate;
    private String loanNo;
    private String landName;
    private String premisesAddress;
    private Integer coverPeriod;
    private Status status;
//    private List<ConstructionDetailItemRequest> constructionDetailsItems;
//    private List<DocumentTypeRequest> documents;
}
