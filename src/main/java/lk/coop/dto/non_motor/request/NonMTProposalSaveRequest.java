package lk.coop.dto.non_motor.request;

import lk.coop.entity.non_motor.NonMTProposalDocument;
import lombok.Data;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Data
@Valid
public class NonMTProposalSaveRequest {
    private String quotationId;
    private String quotationNo;
    private String titleId;
    private String insuredName;
    private String nic;
    private String postalAddress;
    private Date fromDate;
//    private Date toDate;
    private String loanNo;
    private String landName;
    private String premisesAddress;
    private List<ConstructionDetailItemRequest> constructionDetailsItems;
    private List<DocumentTypeRequest> documents;
}
