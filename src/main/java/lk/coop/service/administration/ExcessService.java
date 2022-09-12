package lk.coop.service.administration;


import lk.coop.dto.administration.request.ExcessSaveRequest;
import lk.coop.dto.administration.request.ExcessUpdateRequest;
import lk.coop.dto.administration.response.ExcessResponse;

import java.util.List;

public interface ExcessService {

    ExcessResponse save(ExcessSaveRequest saveRequest);

    ExcessResponse update (ExcessUpdateRequest updateRequest);

    List<ExcessResponse> getAll();

    List<ExcessResponse> getAllActive();

    ExcessResponse findById(String id);

    ExcessResponse delete (String id);
}
