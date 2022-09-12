package lk.coop.service.administration;

import lk.coop.dto.administration.request.InsuranceProductSaveRequest;
import lk.coop.dto.administration.request.InsuranceProductUpdateRequest;
import lk.coop.dto.administration.response.InsuranceProductResponse;


import java.util.List;


public interface InsuranceProductService {

    InsuranceProductResponse save(InsuranceProductSaveRequest saveRequest);

    InsuranceProductResponse update (InsuranceProductUpdateRequest updateRequest);

    List<InsuranceProductResponse> getAll();

    List<InsuranceProductResponse> getAllActive();

    InsuranceProductResponse findById(String id);

    InsuranceProductResponse delete (String id);
}
