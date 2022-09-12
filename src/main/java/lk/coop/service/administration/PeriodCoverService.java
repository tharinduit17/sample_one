package lk.coop.service.administration;

import lk.coop.dto.administration.request.PeriodCoverSaveRequest;
import lk.coop.dto.administration.request.PeriodCoverUpdateRequest;
import lk.coop.dto.administration.response.PeriodCoverResponse;

import java.util.List;

public interface PeriodCoverService {
    PeriodCoverResponse save(PeriodCoverSaveRequest saveRequest);

    PeriodCoverResponse update (PeriodCoverUpdateRequest updateRequest);

    List<PeriodCoverResponse> getAll();

    List<PeriodCoverResponse> getAllActive();

    PeriodCoverResponse findById(String id);

    PeriodCoverResponse delete (String id);
}
