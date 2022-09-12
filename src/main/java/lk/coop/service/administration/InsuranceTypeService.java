package lk.coop.service.administration;

import lk.coop.dto.administration.request.InsuranceTypeSaveRequest;
import lk.coop.dto.administration.request.InsuranceTypeUpdateRequest;
import lk.coop.dto.administration.response.InsuranceTypeResponse;
import lk.coop.entity.administration.InsuranceType;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InsuranceTypeService {

    InsuranceTypeResponse save (InsuranceTypeSaveRequest saveRequest);

    InsuranceTypeResponse update (InsuranceTypeUpdateRequest updateRequest);

    List<InsuranceTypeResponse> getAll();

    List<InsuranceTypeResponse> getAllActive();

    InsuranceTypeResponse findById(String id);

    InsuranceTypeResponse delete (String id);
}
