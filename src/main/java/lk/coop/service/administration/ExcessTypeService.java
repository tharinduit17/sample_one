package lk.coop.service.administration;

import lk.coop.dto.administration.request.ExcessTypeSaveRequest;
import lk.coop.dto.administration.request.ExcessTypeUpdateRequest;
import lk.coop.dto.administration.response.ExcessTypeResponse;

import java.util.List;

public interface ExcessTypeService {

    ExcessTypeResponse save(ExcessTypeSaveRequest saveRequest);

    ExcessTypeResponse update (ExcessTypeUpdateRequest updateRequest);

    List<ExcessTypeResponse> getAll();

    List<ExcessTypeResponse> getAllActive();

    ExcessTypeResponse findById(String id);

    ExcessTypeResponse delete (String id);
}
