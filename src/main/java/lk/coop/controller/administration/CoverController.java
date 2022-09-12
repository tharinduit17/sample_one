package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.request.CoverSaveRequest;
import lk.coop.dto.administration.request.CoverUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.dto.administration.response.CoverResponse;
import lk.coop.service.administration.BankBranchService;
import lk.coop.service.administration.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Cover")
@RestController
@RequestMapping("cover")
public class CoverController {

    @Autowired
    private CoverService coverService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<CoverResponse> save(@RequestBody CoverSaveRequest request) {
        CoverResponse save = coverService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<CoverResponse> update(@RequestBody CoverUpdateRequest updateRequest) {
        CoverResponse update = coverService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<CoverResponse> getOneById(@PathVariable String id) {
        CoverResponse get = coverService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<CoverResponse>> getAllActive() {
        List<CoverResponse> allActive = coverService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<CoverResponse>> getAll() {
        List<CoverResponse> notDeleted = coverService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<CoverResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        CoverResponse coverResponse = coverService.delete(id);

        if (coverResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(coverResponse);
    }


}
