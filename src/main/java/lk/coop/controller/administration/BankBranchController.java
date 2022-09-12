package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.service.administration.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "BankBranch")
@RestController
@RequestMapping("bankBranch")
public class BankBranchController {

    @Autowired
    private BankBranchService bcBankBranchService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<BankBranchResponse> save(@RequestBody BankBranchSaveRequest request) {
        BankBranchResponse save = bcBankBranchService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<BankBranchResponse> update(@RequestBody BankBranchUpdateRequest updateRequest) {
        BankBranchResponse update = bcBankBranchService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<BankBranchResponse> getOneById(@PathVariable String id) {
        BankBranchResponse get = bcBankBranchService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/factive")
    public ResponseEntity<List<BankBranchResponse>> getAllActive() {
        List<BankBranchResponse> allActive = bcBankBranchService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<BankBranchResponse>> getAll() {
        List<BankBranchResponse> notDeleted = bcBankBranchService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<BankBranchResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        BankBranchResponse bcBankBranchResponse = bcBankBranchService.delete(id);

        if (bcBankBranchResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bcBankBranchResponse);
    }


}
