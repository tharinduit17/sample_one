package lk.coop.controller.administration;

import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.PeriodCoverSaveRequest;
import lk.coop.dto.administration.request.PeriodCoverUpdateRequest;
import lk.coop.dto.administration.response.PeriodCoverResponse;
import lk.coop.service.administration.PeriodCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "PeriodCover")
@RestController
@RequestMapping("periodCover")
public class PeriodCoverController {
    @Autowired
    PeriodCoverService periodCoverService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<PeriodCoverResponse> save(@RequestBody PeriodCoverSaveRequest request) {
        PeriodCoverResponse save = periodCoverService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<PeriodCoverResponse> update(@RequestBody PeriodCoverUpdateRequest updateRequest) {
        PeriodCoverResponse update = periodCoverService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<PeriodCoverResponse> getOneById(@PathVariable String id) {
        PeriodCoverResponse get = periodCoverService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<PeriodCoverResponse>> getAllActive() {
        List<PeriodCoverResponse> allActive = periodCoverService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<PeriodCoverResponse>> getAll() {
        List<PeriodCoverResponse> notDeleted = periodCoverService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<PeriodCoverResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        PeriodCoverResponse periodCoverResponse = periodCoverService.delete(id);

        if (periodCoverResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(periodCoverResponse);
    }

}
