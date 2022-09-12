package lk.coop.dto.non_motor.response;

import lk.coop.enums.IsPolicy;
import lk.coop.enums.IsProposal;
import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class QSummeryResponse {
    private String id;
    private BankBranchSumResponse branchName;
    private String quotationNo;
    private String insuredName;
    private Double netInsured;
    private Date issuedDate;
    private Double totalPremium;
    private IsProposal isProposal;
    private IsPolicy isPolicy;
    private Integer coverPeriod;
    private Integer version;
    private Status status;
}
