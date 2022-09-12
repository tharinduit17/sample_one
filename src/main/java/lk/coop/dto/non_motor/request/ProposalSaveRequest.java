package lk.coop.dto.non_motor.request;

import lk.coop.entity.administration.*;
import lk.coop.enums.ConstructionType;
import lombok.Data;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;


@Data
@Valid
public class ProposalSaveRequest {
    private String quotationNo;
    private String title;
    private String insuredName;
    private String nic;
    private String postalAddress;
    private Date fromDate;
    private Date toDate;
    private BankBranchNMTRequest bankBranch;
    private String loanNo;
    private String landName;
    private String premisesAddress;
    private ConstructionType constructionType;

    private Double buildingValue;

//    private String intermediaryCode;
//    private String intermediaryBranchCode;
//    private String intermediaryProductCode;
//    private Integer intermediaryYearCode;
    private Integer seqNo;

    private Double sumInsured;
    private Double solarSumInsured;
//    private String constructionType;

    private Double totalPremium;
    private Integer version;
//    private Status isProposal;
//    private String proposalNo;
//    private Status isPolicy;
//    private String policyNo;
//    private String inactiveReason;
//    private Intermediary intermediary;
    private TitleType titleType;
//    private BankBranch bankBranch;
    private Set<Perils> perils;
    private InsuranceType insuranceType;
    private Set<DocumentType> documentTypes;
    private Excess excess;
}
