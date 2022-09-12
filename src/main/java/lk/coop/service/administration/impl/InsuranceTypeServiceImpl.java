package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.InsuranceTypeSaveRequest;
import lk.coop.dto.administration.request.InsuranceTypeUpdateRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.administration.response.InsuranceTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.InsuranceType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.InsuranceTypeRepository;
import lk.coop.service.administration.InsuranceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class InsuranceTypeServiceImpl implements InsuranceTypeService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Autowired
    private InsuranceTypeRepository insuranceTypeRepository;

    @Override
    public InsuranceTypeResponse save(InsuranceTypeSaveRequest insuranceTypeSaveRequest) {


        try {
            return convertInsuranceType(this.insuranceTypeRepository.save(convertReq(insuranceTypeSaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save InsuranceType {}", insuranceTypeSaveRequest.getInType());
            return null;
        }

    }

    @Override
    public InsuranceTypeResponse update(InsuranceTypeUpdateRequest insuranceTypeUpdateRequest) {

        try {
            InsuranceType insuranceType = this.insuranceTypeRepository.getOne(insuranceTypeUpdateRequest.getId(), INTER_ID);
            if (insuranceType != null) {
                InsuranceType update = this.insuranceTypeRepository.save(convert(insuranceTypeUpdateRequest, insuranceType));
                return convertInsuranceType(update);
            } else {
                log.info("Insurance Type Not Found! {} ", insuranceTypeUpdateRequest.getId());
                return null;
            }

        } catch (Exception e) {
            log.error("Error Update Insurance Type {} ", insuranceTypeUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<InsuranceTypeResponse> getAll() {
        return insuranceTypeRepository.getAllList(Deleted.NO, INTER_ID).stream().map(InsuranceTypeServiceImpl::convertInsuranceType).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceTypeResponse> getAllActive() {
        return insuranceTypeRepository.getActiveList(Status.ACTIVE, Deleted.NO, INTER_ID).stream().map(InsuranceTypeServiceImpl::convertInsuranceType).collect(Collectors.toList());
    }

    @Override
    public InsuranceTypeResponse findById(String id) {
        try {
            InsuranceType insuranceType = insuranceTypeRepository.getOne(id, INTER_ID);
            if (insuranceType != null) {
                return convertInsuranceType(insuranceType);
            } else {
                log.info("NotFound InsuranceType {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById InsuranceType {}", id);
            return null;
        }
    }

    @Override
    public InsuranceTypeResponse delete(String id) {

        try {
            InsuranceType response = insuranceTypeRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                InsuranceType responseDeleted = insuranceTypeRepository.save(response);
                return convertInsuranceType(responseDeleted);
            } else {
                log.info("BOC Insurance Type Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC Insurance Type {} ", id);
            return null;
        }
    }

    /*** FUNCTION */

    /**
     * SAVE
     */
    private static InsuranceType convertReq(InsuranceTypeSaveRequest insuranceTypeSaveRequest) {

        InsuranceType insuranceType = new InsuranceType();

        insuranceType.setInType(insuranceTypeSaveRequest.getInType());
        insuranceType.setInDesc(insuranceTypeSaveRequest.getInDesc());
      //  insuranceType.setIntermediary(convertIntermediary(insuranceTypeSaveRequest.getIntermediary()));
        insuranceType.setStatus(Status.ACTIVE);
        insuranceType.setIsDeleted(Deleted.NO);
        return insuranceType;
    }

    /**
     * UPDATE
     */

    private InsuranceType convert(InsuranceTypeUpdateRequest insuranceTypeUpdateRequest, InsuranceType insuranceType) {
        insuranceType.setId(insuranceTypeUpdateRequest.getId());
        insuranceType.setInType(insuranceTypeUpdateRequest.getInType());
        insuranceType.setInDesc(insuranceTypeUpdateRequest.getInDesc());
        //UPDATE
        insuranceType.setInactiveReason(((insuranceTypeUpdateRequest.getStatus() == Status.ACTIVE) ? "" : insuranceTypeUpdateRequest.getInactiveReason()));
        insuranceType.setStatus(insuranceTypeUpdateRequest.getStatus());
        insuranceType.setIntermediary(convertIntermediary(insuranceTypeUpdateRequest.getIntermediary()));

        return insuranceType;
    }

    /**
     * VIEW
     */
    private static InsuranceTypeResponse convertInsuranceType(InsuranceType insuranceType) {

        if (insuranceType == null) {
            return null;
        }
        return new InsuranceTypeResponse(
                insuranceType.getId(),
                insuranceType.getInType(),
                insuranceType.getInDesc(),
                insuranceType.getStatus(),
                insuranceType.getInactiveReason(),
                insuranceType.getCreatedBy(),
                insuranceType.getCreatedDateTime(),
                insuranceType.getModifiedBy(),
                insuranceType.getModifiedDateTime(),
                convertIntermediaryResponse(insuranceType.getIntermediary())
        );
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

