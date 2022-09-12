package lk.coop.controller.administration;

import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.ExcessTypeSaveRequest;
import lk.coop.dto.administration.request.ExcessTypeUpdateRequest;
import lk.coop.dto.administration.response.ExcessTypeResponse;
import lk.coop.service.administration.ExcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Api(tags = "ExcessType")
@RestController
@RequestMapping("excessType")
public class ExcessTypeController {
    @Autowired
    ExcessTypeService excessTypeService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<ExcessTypeResponse> save(@RequestBody ExcessTypeSaveRequest request) {
        ExcessTypeResponse save = excessTypeService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<ExcessTypeResponse> update(@RequestBody ExcessTypeUpdateRequest updateRequest) {
        ExcessTypeResponse update = excessTypeService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<ExcessTypeResponse> getOneById(@PathVariable String id) {
        ExcessTypeResponse get = excessTypeService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ExcessTypeResponse>> getAllActive() {
        List<ExcessTypeResponse> allActive = excessTypeService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<ExcessTypeResponse>> getAll() {
        List<ExcessTypeResponse> notDeleted = excessTypeService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ExcessTypeResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        ExcessTypeResponse excessResponse = excessTypeService.delete(id);

        if (excessResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(excessResponse);
    }
}
