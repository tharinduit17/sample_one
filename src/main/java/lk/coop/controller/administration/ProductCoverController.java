package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.request.ProductCoverSaveRequest;
import lk.coop.dto.administration.request.ProductCoverUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.dto.administration.response.ProductCoverResponse;
import lk.coop.service.administration.BankBranchService;
import lk.coop.service.administration.ProductCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "ProductCover")
@RestController
@RequestMapping("productCover")
public class ProductCoverController {

    @Autowired
    private ProductCoverService productCoverService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<List<ProductCoverResponse>> save(@Valid @RequestBody List<ProductCoverSaveRequest> saveRequest) {

        List<ProductCoverResponse> save = productCoverService.save(saveRequest);

        return ResponseEntity.ok(save);

    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<ProductCoverResponse> update(@RequestBody ProductCoverUpdateRequest updateRequest) {
        ProductCoverResponse update = productCoverService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    @GetMapping("/getByInsuranceProductId/{insuranceProductId}")
    public ResponseEntity<List<ProductCoverResponse>> getByProductId(@PathVariable("insuranceProductId") @NotBlank String insuranceProductId) {

        List<ProductCoverResponse> pcList = productCoverService.getByProductId(insuranceProductId);

        return ResponseEntity.ok(pcList);

    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductCoverResponse> getOneById(@PathVariable("id") @NotBlank String id) {
        ProductCoverResponse get = productCoverService.getById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }



    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<ProductCoverResponse>> getAll() {
        List<ProductCoverResponse> notDeleted = productCoverService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ProductCoverResponse>> getAllActive() {
        List<ProductCoverResponse> allActive = productCoverService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ProductCoverResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        ProductCoverResponse productCoverResponse = productCoverService.delete(id);

        if (productCoverResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productCoverResponse);
    }

}
