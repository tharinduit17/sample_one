package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.ConstructionDetailsSaveRequest;
import lk.coop.dto.administration.request.ConstructionDetailsUpdateRequest;
import lk.coop.dto.administration.response.ConstructionDetailsResponse;
import lk.coop.service.administration.ConstructionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "ConstructionDetails")
@RestController
@RequestMapping("constructionDetails")
public class ConstructionDetailsController {

    @Autowired
    ConstructionDetailsService constructionDetailsService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<ConstructionDetailsResponse> save(@RequestBody ConstructionDetailsSaveRequest request) {
        ConstructionDetailsResponse save = constructionDetailsService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<ConstructionDetailsResponse> update(@RequestBody ConstructionDetailsUpdateRequest updateRequest) {
        ConstructionDetailsResponse update = constructionDetailsService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<ConstructionDetailsResponse> getOneById(@PathVariable String id) {
        ConstructionDetailsResponse get = constructionDetailsService.getById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ConstructionDetailsResponse>> getAllActive() {
        List<ConstructionDetailsResponse> allActive = constructionDetailsService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<ConstructionDetailsResponse>> getAll() {
        List<ConstructionDetailsResponse> notDeleted = constructionDetailsService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ConstructionDetailsResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        ConstructionDetailsResponse constructionDetailsResponse = constructionDetailsService.delete(id);

        if (constructionDetailsResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(constructionDetailsResponse);
    }


}
