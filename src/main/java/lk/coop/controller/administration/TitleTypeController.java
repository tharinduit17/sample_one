package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.TitleTypeSaveRequest;
import lk.coop.dto.administration.request.TitleTypeUpdateRequest;
import lk.coop.dto.administration.response.TitleTypeResponse;
import lk.coop.service.administration.TitleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "TitleType")
@RestController
@RequestMapping("titleType")
public class TitleTypeController {
    @Autowired
    TitleTypeService titleTypeService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<TitleTypeResponse> save(@RequestBody TitleTypeSaveRequest saveRequest) {
        TitleTypeResponse save = titleTypeService.save(saveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<TitleTypeResponse> update(@RequestBody TitleTypeUpdateRequest updateRequest) {
        TitleTypeResponse update = titleTypeService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<TitleTypeResponse> getOneById(@PathVariable String id) {
        TitleTypeResponse get = titleTypeService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<TitleTypeResponse>> getAllActive() {
        List<TitleTypeResponse> allActive = titleTypeService.getAllActive();
        return ResponseEntity.ok(allActive);
    }

    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<TitleTypeResponse>> getAll() {
        List<TitleTypeResponse> notDeleted = titleTypeService.getAll();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<TitleTypeResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        TitleTypeResponse titleTypeResponse = titleTypeService.delete(id);

        if (titleTypeResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(titleTypeResponse);
    }

}
