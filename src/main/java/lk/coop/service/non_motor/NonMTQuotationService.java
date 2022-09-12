package lk.coop.service.non_motor;

import lk.coop.dto.non_motor.request.NonMTQuotationSaveRequest;
import lk.coop.dto.non_motor.request.NonMTQuotationUpdateRequest;
import lk.coop.dto.non_motor.response.QSummeryResponse;
import lk.coop.dto.non_motor.response.QuotationResponse;

import java.util.List;


public interface NonMTQuotationService {

    QSummeryResponse save(NonMTQuotationSaveRequest saveRequest);

    QSummeryResponse update (NonMTQuotationUpdateRequest updateRequest);

    QSummeryResponse proposal (NonMTQuotationUpdateRequest updateRequest);
//
//    List<NonMTQuotationResponse> getAll();

    List<QSummeryResponse> getAllActive(String bId);

    QuotationResponse findById(String id);
//
//    NonMTQuotationResponse delete (String id);
}
