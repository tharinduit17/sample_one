package lk.coop.dto.non_motor.request;

import lombok.Data;

import javax.validation.Valid;


@Data
@Valid
public class NonMTQuotationProposalRequest {
    private String qid;
    private String proposalNo;
}
