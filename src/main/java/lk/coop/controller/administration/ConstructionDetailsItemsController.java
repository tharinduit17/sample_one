package lk.coop.controller.administration;
import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.ConstructionDetailsItemsSaveRequest;
import lk.coop.dto.administration.request.ConstructionDetailsItemsUpdateRequest;
import lk.coop.dto.administration.response.ConstructionDetailsItemsResponse;
import lk.coop.service.administration.ConstructionDetailsItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Api(tags = "ConstructionDetailsItem")
@RestController
@RequestMapping("constructionDetailsItem")

public class ConstructionDetailsItemsController {

    @Autowired
    ConstructionDetailsItemsService  constructionDetailsItemsService;

    /**
     * Create the Post Mapping
     */

    @PostMapping
    public ResponseEntity<ConstructionDetailsItemsResponse> save(@RequestBody ConstructionDetailsItemsSaveRequest saveRequest) {
        ConstructionDetailsItemsResponse save = constructionDetailsItemsService.save(saveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<ConstructionDetailsItemsResponse> update(@RequestBody ConstructionDetailsItemsUpdateRequest updateRequest) {
        ConstructionDetailsItemsResponse update = constructionDetailsItemsService.update(updateRequest);
        return ResponseEntity.ok(update);
    }
    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<ConstructionDetailsItemsResponse> getOneById(@PathVariable String id) {
        ConstructionDetailsItemsResponse get = constructionDetailsItemsService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ConstructionDetailsItemsResponse>> getAllActive() {
        List<ConstructionDetailsItemsResponse> allActive = constructionDetailsItemsService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<ConstructionDetailsItemsResponse>> getAll() {
        List<ConstructionDetailsItemsResponse> notDeleted = constructionDetailsItemsService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ConstructionDetailsItemsResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        ConstructionDetailsItemsResponse constructionDetailsItemsResponse = constructionDetailsItemsService.delete(id);

        if (constructionDetailsItemsResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(constructionDetailsItemsResponse);
    }
}

