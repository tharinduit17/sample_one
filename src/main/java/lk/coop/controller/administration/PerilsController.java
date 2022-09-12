package lk.coop.controller.administration;

import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.PerilsSaveRequest;
import lk.coop.dto.administration.request.PerilsUpdateRequest;
import lk.coop.dto.administration.response.PerilsResponse;
import lk.coop.service.administration.PerilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Api(tags = "Perils")
@RestController
@RequestMapping("perils")
public class PerilsController {
    @Autowired
    PerilsService perilsService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<PerilsResponse> save(@RequestBody PerilsSaveRequest saveRequest) {
        PerilsResponse save = perilsService.save(saveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<PerilsResponse> update(@RequestBody PerilsUpdateRequest updateRequest) {
        PerilsResponse update = perilsService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<PerilsResponse> getOneById(@PathVariable String id) {
        PerilsResponse get = perilsService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<PerilsResponse>> getAllActive() {
        List<PerilsResponse> allActive = perilsService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<PerilsResponse>> getAll() {
        List<PerilsResponse> notDeleted = perilsService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<PerilsResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        PerilsResponse perilsResponse = perilsService.delete(id);

        if (perilsResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(perilsResponse);
    }
}
