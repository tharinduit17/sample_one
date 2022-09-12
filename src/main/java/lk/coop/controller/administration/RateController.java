package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.RateSaveRequest;
import lk.coop.dto.administration.request.RateUpdateRequest;
import lk.coop.dto.administration.response.RateResponse;
import lk.coop.service.administration.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Rate")
@RestController
@RequestMapping("rate")
public class RateController {

    @Autowired
    private RateService rateService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<RateResponse> save(@RequestBody RateSaveRequest saveRequest) {
        RateResponse save = rateService.save(saveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<RateResponse> update(@RequestBody RateUpdateRequest updateRequest) {
        RateResponse update = rateService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<RateResponse> getOneById(@PathVariable String id) {
        RateResponse get = rateService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<RateResponse>> getAllActive() {
        List<RateResponse> allActive = rateService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<RateResponse>> getAll() {
        List<RateResponse> notDeleted = rateService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Get Range
     */
//    @GetMapping("{productid}/{to}")
//    public ResponseEntity<List<RateResponse>> getRateRange(@PathVariable("productid") @NotBlank String productid,@PathVariable("to") @NotBlank Integer to) {
//        List<RateResponse> notDeleted = rateService.getRateRange(productid,to);
//        return ResponseEntity.ok(notDeleted);
//    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<RateResponse> deleteById(@PathVariable("id") @NotBlank String id) {
        RateResponse rateResponse = rateService.delete(id);
        if (rateResponse == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rateResponse);
    }

}

