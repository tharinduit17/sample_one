package lk.coop.service.administration;


import lk.coop.dto.administration.request.ConstructionDetailsSaveRequest;
import lk.coop.dto.administration.request.ConstructionDetailsUpdateRequest;
import lk.coop.dto.administration.response.ConstructionDetailsResponse;

import java.util.List;

public interface ConstructionDetailsService {

    ConstructionDetailsResponse save (ConstructionDetailsSaveRequest saveRequest);

    ConstructionDetailsResponse update (ConstructionDetailsUpdateRequest updateRequest);

    List<ConstructionDetailsResponse> getAll();

    List<ConstructionDetailsResponse> getAllActive();

    ConstructionDetailsResponse getById(String id);

    ConstructionDetailsResponse delete (String id);

}
