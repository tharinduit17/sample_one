package lk.coop.service.administration;

import lk.coop.dto.administration.request.DocumentTypeSaveRequest;
import lk.coop.dto.administration.request.DocumentTypeUpdateRequest;
import lk.coop.dto.administration.response.DocumentTypeResponse;

import java.util.List;

public interface DocumentTypeService {
    DocumentTypeResponse save(DocumentTypeSaveRequest saveRequest);

    DocumentTypeResponse update (DocumentTypeUpdateRequest updateRequest);

    List<DocumentTypeResponse> getAll();

    List<DocumentTypeResponse> getAllActive();

    DocumentTypeResponse findById(String id);

    DocumentTypeResponse delete (String id);

}
