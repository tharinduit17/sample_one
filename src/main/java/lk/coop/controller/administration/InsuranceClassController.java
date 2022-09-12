package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.InsuranceClassSaveRequest;
import lk.coop.dto.administration.request.InsuranceClassUpdateRequest;
import lk.coop.dto.administration.response.InsuranceClassResponse;
import lk.coop.service.administration.InsuranceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Insurance Class")
@RestController
@RequestMapping("insuranceClass")
public class  InsuranceClassController {

    @Autowired
    private InsuranceClassService insuranceClassService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<InsuranceClassResponse> save(@RequestBody InsuranceClassSaveRequest request) {
        InsuranceClassResponse save = this.insuranceClassService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<InsuranceClassResponse> update(@RequestBody InsuranceClassUpdateRequest updateRequest) {
        InsuranceClassResponse update = this.insuranceClassService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by ID Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<InsuranceClassResponse> getOneById(@PathVariable @NotBlank String id) {
        InsuranceClassResponse get = this.insuranceClassService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<InsuranceClassResponse>> getAllActive() {
        List<InsuranceClassResponse> allActive = this.insuranceClassService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<InsuranceClassResponse>> getAll() {
        List<InsuranceClassResponse> notDeleted = this.insuranceClassService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create to Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<InsuranceClassResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        InsuranceClassResponse bcInsuranceClassResponse = this.insuranceClassService.delete(id);

        if (bcInsuranceClassResponse == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bcInsuranceClassResponse);
    }


}
