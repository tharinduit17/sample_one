package lk.coop.service.administration;

import lk.coop.dto.administration.request.ConstructionDetailsItemsSaveRequest;
import lk.coop.dto.administration.request.ConstructionDetailsItemsUpdateRequest;
import lk.coop.dto.administration.response.ConstructionDetailsItemsResponse;

import java.util.List;

public interface ConstructionDetailsItemsService {

    ConstructionDetailsItemsResponse save(ConstructionDetailsItemsSaveRequest constructionDetailsItemsSaveRequest);

    ConstructionDetailsItemsResponse update (ConstructionDetailsItemsUpdateRequest constructionDetailsItemsUpdateRequest);

    List<ConstructionDetailsItemsResponse> getAll();

    List<ConstructionDetailsItemsResponse> getAllActive();

    ConstructionDetailsItemsResponse findById(String id);

    ConstructionDetailsItemsResponse delete (String id);
}
