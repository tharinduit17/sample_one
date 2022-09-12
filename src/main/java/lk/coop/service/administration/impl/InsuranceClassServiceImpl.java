package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.InsuranceClassSaveRequest;
import lk.coop.dto.administration.request.InsuranceClassUpdateRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.administration.response.InsuranceClassResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.InsuranceClass;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.InsuranceClassRepository;
import lk.coop.service.administration.InsuranceClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsuranceClassServiceImpl implements InsuranceClassService {

    @Autowired
    private InsuranceClassRepository insuranceClassRepository;

    @Override
    public InsuranceClassResponse save(InsuranceClassSaveRequest bcInsuranceClassSaveRequest) {
        try {
            return convertBcInsuranceClass(this.insuranceClassRepository.save(convertReq(bcInsuranceClassSaveRequest)));
        } catch (Exception e) {
            log.error("Error Save InsuranceClass {}", bcInsuranceClassSaveRequest.getName());
            return null;
        }
    }

    @Override
    public InsuranceClassResponse update(InsuranceClassUpdateRequest insuranceClassUpdateRequest) {

        try {
            InsuranceClass bcInsuranceClass = this.insuranceClassRepository.getOne(Deleted.NO, insuranceClassUpdateRequest.getId());
            if (bcInsuranceClass != null) {
                InsuranceClass update = this.insuranceClassRepository.save(convert(insuranceClassUpdateRequest, bcInsuranceClass));
                return convertBcInsuranceClass(update);
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
    public List<InsuranceClassResponse> getAllActive() {
        return insuranceClassRepository.findByStatusAndIsDeleted(Status.ACTIVE, Deleted.NO).stream().map(InsuranceClassServiceImpl::convertBcInsuranceClass).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceClassResponse> getAll() {
        return insuranceClassRepository.findByIsDeleted(Deleted.NO).stream().map(InsuranceClassServiceImpl::convertBcInsuranceClass).collect(Collectors.toList());
    }

    @Override
    public InsuranceClassResponse findById(String id) {
        try {
            InsuranceClass insuranceClass = insuranceClassRepository.getOne(Deleted.NO, id);
            if (insuranceClass != null) {
                return convertBcInsuranceClass(insuranceClass);
            } else {
                log.info("NotFound InsuranceClass {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById InsuranceClass {}", id);
            return null;
        }
    }

    @Override
    public InsuranceClassResponse delete(String id) {
        try {
            InsuranceClass response = insuranceClassRepository.getOne(Deleted.NO, id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                InsuranceClass responseDeleted = insuranceClassRepository.save(response);
                return convertBcInsuranceClass(responseDeleted);
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
    private static InsuranceClass convertReq(InsuranceClassSaveRequest insuranceClassSaveRequest) {
        InsuranceClass insuranceClass = new InsuranceClass();
        insuranceClass.setCode(insuranceClassSaveRequest.getCode());
        insuranceClass.setName(insuranceClassSaveRequest.getName());
        insuranceClass.setDescription(insuranceClassSaveRequest.getDescription());
        insuranceClass.setStatus(Status.ACTIVE);
        insuranceClass.setIsDeleted(Deleted.NO);
        return insuranceClass;
    }

    /**
     * UPDATE
     */
    private InsuranceClass convert(InsuranceClassUpdateRequest bcInsuranceClassUpdateRequest, InsuranceClass bcInsuranceClass) {
        bcInsuranceClass.setCode(bcInsuranceClassUpdateRequest.getCode());
        bcInsuranceClass.setName(bcInsuranceClassUpdateRequest.getName());
        bcInsuranceClass.setDescription(bcInsuranceClassUpdateRequest.getDescription());
        //UPDATE
        bcInsuranceClass.setStatus(bcInsuranceClassUpdateRequest.getStatus());
        bcInsuranceClass.setInactiveReason(((bcInsuranceClassUpdateRequest.getStatus() == Status.ACTIVE) ? "" : bcInsuranceClassUpdateRequest.getInactiveReason()));

        return bcInsuranceClass;
    }

    /**
     * VIEW
     */
    private static InsuranceClassResponse convertBcInsuranceClass(InsuranceClass bcInsuranceClass) {
        if (bcInsuranceClass == null) {
            return null;
        }
        return new InsuranceClassResponse(bcInsuranceClass.getId(), bcInsuranceClass.getCode(), bcInsuranceClass.getName(),
                bcInsuranceClass.getDescription(), bcInsuranceClass.getStatus(), bcInsuranceClass.getCreatedBy(),
                bcInsuranceClass.getCreatedDateTime(), bcInsuranceClass.getModifiedBy(), bcInsuranceClass.getModifiedDateTime());
    }


}
