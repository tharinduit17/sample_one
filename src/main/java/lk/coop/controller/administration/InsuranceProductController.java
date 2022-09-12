package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.InsuranceProductSaveRequest;
import lk.coop.dto.administration.request.InsuranceProductUpdateRequest;
import lk.coop.dto.administration.response.InsuranceProductResponse;
import lk.coop.service.administration.InsuranceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Insurance Product")
@RestController
@RequestMapping("insuranceProduct")
public class InsuranceProductController {


    @Autowired
    private InsuranceProductService insuranceProductService;


    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<InsuranceProductResponse> save(@RequestBody InsuranceProductSaveRequest request) {
        InsuranceProductResponse save = this.insuranceProductService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<InsuranceProductResponse> update(@RequestBody InsuranceProductUpdateRequest updateRequest) {
        InsuranceProductResponse update = this.insuranceProductService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by ID Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<InsuranceProductResponse> getOneById(@PathVariable @NotBlank String id) {
        InsuranceProductResponse get = this.insuranceProductService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<InsuranceProductResponse>> getAllActive() {
        List<InsuranceProductResponse> allActive = this.insuranceProductService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<InsuranceProductResponse>> getAll() {
        List<InsuranceProductResponse> notDeleted = this.insuranceProductService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create to Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<InsuranceProductResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        InsuranceProductResponse bcInsuranceProductResponse = this.insuranceProductService.delete(id);

        if (bcInsuranceProductResponse == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bcInsuranceProductResponse);
    }
}
