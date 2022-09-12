package lk.coop.service.administration;


import lk.coop.dto.administration.request.TitleTypeSaveRequest;
import lk.coop.dto.administration.request.TitleTypeUpdateRequest;
import lk.coop.dto.administration.response.TitleTypeResponse;

import java.util.List;

public interface TitleTypeService {

    TitleTypeResponse save(TitleTypeSaveRequest saveRequest);

    TitleTypeResponse update (TitleTypeUpdateRequest updateRequest);

    List<TitleTypeResponse> getAllActive();

    TitleTypeResponse findById(String id);

    TitleTypeResponse delete (String id);

    List<TitleTypeResponse> getAll();
}
