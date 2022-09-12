package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.DocumentTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.DocumentType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.DocumentTypeRepository;
import lk.coop.service.administration.DocumentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private static final String INTER_ID = "1";

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentTypeResponse save(DocumentTypeSaveRequest documentTypeSaveRequest) {
        try {
            return convertDocumentType(this.documentTypeRepository.save(convertReq(documentTypeSaveRequest)));

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error Save DocumentType {}", documentTypeSaveRequest.getDcType());
            return null;

        }
    }

    @Override
    public DocumentTypeResponse update(DocumentTypeUpdateRequest documentTypeUpdateRequest) {
        try {
            DocumentType documentType = this.documentTypeRepository.getOne(documentTypeUpdateRequest.getId(), INTER_ID);
            if (documentType != null) {
                DocumentType update = this.documentTypeRepository.save(convert(documentTypeUpdateRequest, documentType));
                return convertDocumentType(update);
            } else {
                log.info("BOC Document Type Not Found! {} ", documentTypeUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Document Type {} ", documentTypeUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<DocumentTypeResponse> getAll() {
        return documentTypeRepository.getAllList(Deleted.NO,INTER_ID).stream().map
                (DocumentTypeServiceImpl::convertDocumentType).collect(Collectors.toList());

    }

    @Override
    public List<DocumentTypeResponse> getAllActive() {
        return documentTypeRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().map(DocumentTypeServiceImpl::convertDocumentType).collect(Collectors.toList());

    }

    @Override
    public DocumentTypeResponse findById(String id) {
        try {
            DocumentType documentType = documentTypeRepository.getOne(id, INTER_ID);
            if (documentType != null) {
                return convertDocumentType(documentType);
            } else {
                log.info("NotFound Document Type {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Document Type {}", id);
            return null;
        }
    }

    @Override
    public DocumentTypeResponse delete(String id) {
        try {
            DocumentType response = documentTypeRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                DocumentType responseDeleted = documentTypeRepository.save(response);
                return convertDocumentType(responseDeleted);
            } else {
                log.info("BOC  Document Type Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC  Document Type {} ", id);
            return null;
        }
    }
    /**
     * SAVE
     */
    private static DocumentType convertReq(DocumentTypeSaveRequest documentTypeSaveRequest) {
        DocumentType documentType = new DocumentType();
        documentType.setDcType(documentTypeSaveRequest.getDcType());
        documentType.setDcDesc(documentTypeSaveRequest.getDcDesc());
        documentType.setStatus(Status.ACTIVE);
        documentType.setIntermediary(convertIntermediary(documentTypeSaveRequest.getIntermediary()));
        documentType.setInactiveReason(documentType.getInactiveReason());
        documentType.setIsDeleted(Deleted.NO);
        return documentType;
    }
    /**
     * UPDATE
     */
    private DocumentType convert(DocumentTypeUpdateRequest documentTypeUpdateRequest, DocumentType documentType) {
        documentType.setId(documentTypeUpdateRequest.getId());
        documentType.setDcType(documentTypeUpdateRequest.getDcType());
        documentType.setDcDesc(documentTypeUpdateRequest.getDcDesc());
        //UPDATE
        documentType.setInactiveReason(((documentTypeUpdateRequest.getStatus() == Status.ACTIVE) ? "" : documentTypeUpdateRequest.getInactiveReson()));
        documentType.setStatus(documentTypeUpdateRequest.getStatus());
        documentType.setIntermediary(convertIntermediary(documentTypeUpdateRequest.getIntermediary()));

        return documentType;
    }
    /**
     * VIEW
     */
    private static DocumentTypeResponse convertDocumentType (DocumentType documentType) {
        if (documentType == null) {
            return null;
        }
        return new DocumentTypeResponse(

                documentType.getId(),
                documentType.getDcType(),
                documentType.getDcDesc(),
                documentType.getStatus(),
                convertIntermediaryResponse(documentType.getIntermediary()),
                documentType.getInactiveReason(),
                documentType.getCreatedBy(),
                documentType.getCreatedDateTime(),
                documentType.getModifiedBy(),
                documentType.getModifiedDateTime());
    }
    private static IntermediaryResponse convertIntermediaryResponse(Intermediary intermediary) {
        if (intermediary == null) {
            return null;
        }
        return new IntermediaryResponse(intermediary.getId(), intermediary.getName(), intermediary.getDescription(),
                intermediary.getEmail(),intermediary.getStatus(), intermediary.getCreatedBy(), intermediary.getCreatedDateTime(),
                intermediary.getModifiedBy(), intermediary.getModifiedDateTime());
    }

    private static Intermediary convertIntermediary(IntermediaryRequest intermediaryRequest) {
        if (intermediaryRequest == null) {
            return null;
        }
        Intermediary intermediary = new Intermediary();
        intermediary.setId(intermediaryRequest.getId());

        return intermediary;
    }

}
