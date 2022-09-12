package lk.coop.service.administration;

import lk.coop.dto.administration.request.PerilsSaveRequest;
import lk.coop.dto.administration.request.PerilsUpdateRequest;
import lk.coop.dto.administration.response.PerilsResponse;

import java.util.List;

public interface PerilsService {
    PerilsResponse save(PerilsSaveRequest saveRequest);

    PerilsResponse update (PerilsUpdateRequest updateRequest);

    List<PerilsResponse> getAll();

    List<PerilsResponse> getAllActive();

    PerilsResponse findById(String id);

    PerilsResponse delete (String id);
}
