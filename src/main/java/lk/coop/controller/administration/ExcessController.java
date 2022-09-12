package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.ExcessSaveRequest;
import lk.coop.dto.administration.request.ExcessUpdateRequest;
import lk.coop.dto.administration.response.ExcessResponse;
import lk.coop.service.administration.ExcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Excess")
@RestController
@RequestMapping("excess")
public class ExcessController {

    @Autowired
    private ExcessService excessService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<ExcessResponse> save(@RequestBody ExcessSaveRequest saveRequest) {
        ExcessResponse save = excessService.save(saveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<ExcessResponse> update(@RequestBody ExcessUpdateRequest updateRequest) {
        ExcessResponse update = excessService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<ExcessResponse> getOneById(@PathVariable String id) {
        ExcessResponse get = excessService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ExcessResponse>> getAllActive() {
        List<ExcessResponse> allActive = excessService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<ExcessResponse>> getAll() {
        List<ExcessResponse> notDeleted = excessService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ExcessResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        ExcessResponse excessResponse = excessService.delete(id);

        if (excessResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(excessResponse);
    }

}

