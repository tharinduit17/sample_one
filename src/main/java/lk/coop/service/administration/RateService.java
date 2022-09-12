package lk.coop.service.administration;


import lk.coop.dto.administration.request.RateSaveRequest;
import lk.coop.dto.administration.request.RateUpdateRequest;
import lk.coop.dto.administration.response.RateResponse;
import lk.coop.dto.administration.response.RateSummaryResponse;

import java.util.List;

public interface RateService {
    RateResponse save(RateSaveRequest saveRequest);
    RateResponse update (RateUpdateRequest updateRequest);
    List<RateResponse> getAll();
    List<RateResponse> getAllActive();
    List<RateSummaryResponse> getRateRange(String productId, Integer to);
    RateResponse findById(String id);
    RateResponse delete (String id);
}
