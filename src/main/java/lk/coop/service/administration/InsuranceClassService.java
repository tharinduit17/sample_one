package lk.coop.service.administration;

import lk.coop.dto.administration.request.InsuranceClassSaveRequest;
import lk.coop.dto.administration.request.InsuranceClassUpdateRequest;
import lk.coop.dto.administration.response.InsuranceClassResponse;

import java.util.List;


public interface InsuranceClassService {

    InsuranceClassResponse save(InsuranceClassSaveRequest saveRequest);

    InsuranceClassResponse update (InsuranceClassUpdateRequest updateRequest);

    List<InsuranceClassResponse> getAll();

    List<InsuranceClassResponse> getAllActive();

    InsuranceClassResponse findById(String id);

    InsuranceClassResponse delete (String id);
}
