package lk.coop.service.administration;

import lk.coop.dto.administration.request.IntermediarySaveRequest;
import lk.coop.dto.administration.request.IntermediaryUpdateRequest;
import lk.coop.dto.administration.response.IntermediaryResponse;

import java.util.List;


public interface IntermediaryService {

    IntermediaryResponse save(IntermediarySaveRequest saveRequest);

    IntermediaryResponse update (IntermediaryUpdateRequest updateRequest);

    List<IntermediaryResponse> getAll();

    List<IntermediaryResponse> getAllActive();

    IntermediaryResponse findById(String id);

    IntermediaryResponse delete (String id);
}
