package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.DocumentTypeSaveRequest;
import lk.coop.dto.administration.request.DocumentTypeUpdateRequest;
import lk.coop.dto.administration.response.DocumentTypeResponse;
import lk.coop.service.administration.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "DocumentType")
@RestController
@RequestMapping("documentType")

public class DocumentTypeController {

    @Autowired
    DocumentTypeService documentTypeService;

    /**
     * Create the Post Mapping
     */
    @PostMapping
    public ResponseEntity<DocumentTypeResponse> save(@RequestBody DocumentTypeSaveRequest saveRequest) {
        DocumentTypeResponse save = documentTypeService.save(saveRequest);
        return ResponseEntity.ok(save);
    }
    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<DocumentTypeResponse> update(@RequestBody DocumentTypeUpdateRequest updateRequest) {
        DocumentTypeResponse update = documentTypeService.update(updateRequest);
        return ResponseEntity.ok(update);
    }
    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<DocumentTypeResponse> getOneById(@PathVariable String id) {
        DocumentTypeResponse get = documentTypeService.findById(id);
        if (get == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }
    /**
     * Create the Get All Active
     */
    @GetMapping("/active")
    public ResponseEntity<List<DocumentTypeResponse>> getAllActive() {
        List<DocumentTypeResponse> allActive = documentTypeService.getAllActive();
        return ResponseEntity.ok(allActive);
    }
    /**
     * Create the Get All
     */
    @GetMapping
    public ResponseEntity<List<DocumentTypeResponse>> getAll() {
        List<DocumentTypeResponse> notDeleted = documentTypeService.getAll();
        return ResponseEntity.ok(notDeleted);
    }
    /**
     * Create the Delete Mapping
     */
    @DeleteMapping("{id}")
    public ResponseEntity<DocumentTypeResponse> deleteById(@PathVariable("id") @NotBlank String id) {

        DocumentTypeResponse documentTypeResponse = documentTypeService.delete(id);

        if (documentTypeResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documentTypeResponse);
    }
}
