package lk.coop.service.administration;

import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.entity.administration.BankBranch;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankBranchService {

    BankBranchResponse save(BankBranchSaveRequest saveRequest);

    BankBranchResponse update (BankBranchUpdateRequest updateRequest);

    List<BankBranchResponse> getAll();

    List<BankBranchResponse> getAllActive();

    BankBranchResponse findById(String id);

    BankBranchResponse delete (String id);
}
