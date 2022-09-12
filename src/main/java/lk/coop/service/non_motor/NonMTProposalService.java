package lk.coop.service.non_motor;

import lk.coop.dto.non_motor.request.NonMTProposalSaveRequest;
import lk.coop.dto.non_motor.response.PSummeryResponse;
import lk.coop.dto.non_motor.response.ProposalResponse;

import java.util.List;


public interface NonMTProposalService {

    PSummeryResponse save(NonMTProposalSaveRequest saveRequest);

//    QSummeryResponse update (NonMTQuotationUpdateRequest updateRequest);
//
//    QSummeryResponse proposal (NonMTQuotationUpdateRequest updateRequest);
//
//    List<NonMTQuotationResponse> getAll();

    List<PSummeryResponse> getAllActive(String bId);

    ProposalResponse findById(String id);

//    NonMTQuotationResponse delete (String id);
}
