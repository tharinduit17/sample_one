package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.InsuranceProductSaveRequest;
import lk.coop.dto.administration.request.InsuranceProductUpdateRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.administration.response.InsuranceProductResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.InsuranceClass;
import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.InsuranceProductRepository;
import lk.coop.service.administration.InsuranceProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsuranceProductServiceImpl implements InsuranceProductService {
    @Value("${intermediary.id}")
    private String INTER_ID;

    @Autowired
    private InsuranceProductRepository insuranceClassRepository;

    @Override
    public InsuranceProductResponse save(InsuranceProductSaveRequest insuranceProductSaveRequest) {
        try {
            return convertBcInsuranceProduct(this.insuranceClassRepository.save(convertReq(insuranceProductSaveRequest)));
        } catch (Exception e) {
            log.error("Error Save InsuranceProduct {}", insuranceProductSaveRequest.getProName());
            return null;
        }
    }

    @Override
    public InsuranceProductResponse update(InsuranceProductUpdateRequest insuranceClassUpdateRequest) {

        try {
            InsuranceProduct bcInsuranceProduct = this.insuranceClassRepository.getOne(insuranceClassUpdateRequest.getId(), INTER_ID, Deleted.NO);
            if (bcInsuranceProduct != null) {
                InsuranceProduct update = this.insuranceClassRepository.save(convert(insuranceClassUpdateRequest, bcInsuranceProduct));
                return convertBcInsuranceProduct(update);
            } else {
                log.info("BOC Bank Branch Not Found! {} ", insuranceClassUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Bank Branch {} ", insuranceClassUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<InsuranceProductResponse> getAllActive() {
        return insuranceClassRepository.getActiveList(Status.ACTIVE, Deleted.NO, INTER_ID).stream().map(InsuranceProductServiceImpl::convertBcInsuranceProduct).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceProductResponse> getAll() {
        return insuranceClassRepository.getAllList(Deleted.NO, INTER_ID).stream().map(InsuranceProductServiceImpl::convertBcInsuranceProduct).collect(Collectors.toList());
    }

    @Override
    public InsuranceProductResponse findById(String id) {
        try {
            InsuranceProduct insuranceClass = insuranceClassRepository.getOne(id, INTER_ID, Deleted.NO);
            if (insuranceClass != null) {
                return convertBcInsuranceProduct(insuranceClass);
            } else {
                log.info("NotFound InsuranceProduct {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById InsuranceProduct {}", id);
            return null;
        }
    }

    @Override
    public InsuranceProductResponse delete(String id) {
        try {
            InsuranceProduct response = insuranceClassRepository.getOne(id, INTER_ID, Deleted.NO);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                InsuranceProduct responseDeleted = insuranceClassRepository.save(response);
                return convertBcInsuranceProduct(responseDeleted);
            } else {
                log.info("BOC Bank Branch Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC Bank Branch {} ", id);
            return null;
        }
    }

    /*** FUNCTION */

    /**
     * SAVE
     */
    private static InsuranceProduct convertReq(InsuranceProductSaveRequest insuranceProductSaveRequest) {

        InsuranceProduct insuranceProduct = new InsuranceProduct();

        insuranceProduct.setProCode(insuranceProductSaveRequest.getProCode());
        insuranceProduct.setProName(insuranceProductSaveRequest.getProName());
        insuranceProduct.setProDescription(insuranceProductSaveRequest.getProDescription());
        insuranceProduct.setInsuranceClass(convertInsuranceClass(insuranceProductSaveRequest.getInsuranceClassId()));
        insuranceProduct.setIntermediary(convertIntermediary(insuranceProductSaveRequest.getIntermediary()));
        insuranceProduct.setStatus(Status.ACTIVE);
        insuranceProduct.setIsDeleted(Deleted.NO);

        return insuranceProduct;
    }

    /**
     * UPDATE
     */
    private InsuranceProduct convert(InsuranceProductUpdateRequest insuranceProductUpdateRequest, InsuranceProduct insuranceProduct) {
        insuranceProduct.setProCode(insuranceProductUpdateRequest.getProCode());
        insuranceProduct.setProName(insuranceProductUpdateRequest.getProName());
        insuranceProduct.setProDescription(insuranceProductUpdateRequest.getProDescription());
        insuranceProduct.setInsuranceClass(convertInsuranceClass(insuranceProductUpdateRequest.getInsuranceClassId()));
        insuranceProduct.setIntermediary(convertIntermediary(insuranceProductUpdateRequest.getIntermediary()));
        //UPDATE
        insuranceProduct.setStatus(insuranceProductUpdateRequest.getStatus());
        insuranceProduct.setInactiveReason(((insuranceProductUpdateRequest.getStatus() == Status.ACTIVE) ? "" : insuranceProductUpdateRequest.getInactiveReason()));

        return insuranceProduct;
    }

    /**
     * VIEW
     */
    private static InsuranceProductResponse convertBcInsuranceProduct(InsuranceProduct insuranceProduct) {

        if (insuranceProduct == null) {
            return null;
        }
        return new InsuranceProductResponse(
                insuranceProduct.getId(),
                insuranceProduct.getProCode(),
                insuranceProduct.getProName(),
                insuranceProduct.getProDescription(),
                insuranceProduct.getStatus(),
                convertIntermediaryResponse(insuranceProduct.getIntermediary()),
                insuranceProduct.getCreatedBy(),
                insuranceProduct.getCreatedDateTime(),
                insuranceProduct.getModifiedBy(),
                insuranceProduct.getModifiedDateTime(),
                insuranceProduct.getInactiveReason()
        );
    }

    private static IntermediaryResponse convertIntermediaryResponse(Intermediary intermediary) {

        if (intermediary == null) {
            return null;
        }

        return new IntermediaryResponse(
                intermediary.getId(),
                intermediary.getName(),
                intermediary.getDescription(),
                intermediary.getEmail(),
                intermediary.getStatus(),
                intermediary.getCreatedBy(),
                intermediary.getCreatedDateTime(),
                intermediary.getModifiedBy(),
                intermediary.getModifiedDateTime()
        );
    }

    private static InsuranceClass convertInsuranceClass(String classId) {
        if (classId == null) {
            return null;
        }
        InsuranceClass insuranceClass = new InsuranceClass();
        insuranceClass.setId(classId);
        return insuranceClass;
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
