package lk.coop.service.administration;

import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.request.CoverSaveRequest;
import lk.coop.dto.administration.request.CoverUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.dto.administration.response.CoverResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoverService {

    CoverResponse save(CoverSaveRequest saveRequest);

    CoverResponse update (CoverUpdateRequest updateRequest);

    List<CoverResponse> getAll();

    List<CoverResponse> getAllActive();

    CoverResponse findById(String id);

    CoverResponse delete (String id);
}
