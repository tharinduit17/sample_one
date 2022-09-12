package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.ExcessTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.ExcessType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.ExcessTypeRepository;
import lk.coop.service.administration.ExcessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcessTypeServiceImpl implements ExcessTypeService {
    private static final String INTER_ID = "1";
    @Autowired
    private ExcessTypeRepository excessTypeRepository;

    @Override
    public ExcessTypeResponse save(ExcessTypeSaveRequest excessTypeSaveRequest) {
        try {
            return convertExcessType(this.excessTypeRepository.save(convertReq(excessTypeSaveRequest)));

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error Save ExcessType {}", excessTypeSaveRequest.getExType());
            return null;

        }
    }

    @Override
    public ExcessTypeResponse update(ExcessTypeUpdateRequest excessTypeUpdateRequest) {
        try {
            ExcessType excessType = this.excessTypeRepository.getOne(excessTypeUpdateRequest.getId(), INTER_ID);
            if (excessType != null) {
                ExcessType update = this.excessTypeRepository.save(convert(excessTypeUpdateRequest, excessType));
                return convertExcessType(update);
            } else {
                log.info("BOC Excess Type Not Found! {} ", excessTypeUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Excess Type {} ", excessTypeUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<ExcessTypeResponse> getAll() {
        return excessTypeRepository.getAllList(Deleted.NO,INTER_ID).stream().map(ExcessTypeServiceImpl::convertExcessType).collect(Collectors.toList());
    }

    @Override
    public List<ExcessTypeResponse> getAllActive() {
        return excessTypeRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().map(ExcessTypeServiceImpl::convertExcessType).collect(Collectors.toList());
    }

    @Override
    public ExcessTypeResponse findById(String id) {
        try {
            ExcessType excessType = excessTypeRepository.getOne(id, INTER_ID);
            if (excessType != null) {
                return convertExcessType(excessType);
            } else {
                log.info("NotFound ExcessType {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById ExcessType {}", id);
            return null;
        }
    }

    @Override
    public ExcessTypeResponse delete(String id) {
        try {
            ExcessType response = excessTypeRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                ExcessType responseDeleted = excessTypeRepository.save(response);
                return convertExcessType(responseDeleted);
            } else {
                log.info("BOC  Excess Type Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC  Excess Type {} ", id);
            return null;
        }
    }
    /*** FUNCTION */

    /**
     * SAVE
     */
    private static ExcessType convertReq(ExcessTypeSaveRequest excessTypeSaveRequest) {
        ExcessType excessType = new ExcessType();
        excessType.setExType(excessTypeSaveRequest.getExType());
        excessType.setExDesc(excessTypeSaveRequest.getExDesc());
        excessType.setInactiveReason(excessType.getInactiveReason());
        excessType.setIntermediary(convertIntermediary(excessTypeSaveRequest.getIntermediary()));
        excessType.setStatus(Status.ACTIVE);
        excessType.setIsDeleted(Deleted.NO);
        return excessType;
    }

    /**
     * UPDATE
     */
    private ExcessType convert(ExcessTypeUpdateRequest excessTypeUpdateRequest, ExcessType excessType) {
        excessType.setId(excessTypeUpdateRequest.getId());
        excessType.setExType(excessTypeUpdateRequest.getExType());
        excessType.setExDesc(excessTypeUpdateRequest.getExDesc());
        //UPDATE
        excessType.setInactiveReason(((excessTypeUpdateRequest.getStatus() == Status.ACTIVE) ? "" : excessTypeUpdateRequest.getInactiveReson()));
        excessType.setStatus(excessTypeUpdateRequest.getStatus());
        excessType.setIntermediary(convertIntermediary(excessTypeUpdateRequest.getIntermediary()));

        return excessType;
    }
    /**
     * VIEW
     */
    private static ExcessTypeResponse convertExcessType (ExcessType excessType) {
        if (excessType == null) {
            return null;
        }
        return new ExcessTypeResponse(
                excessType.getId(),
                excessType.getExType(),
                excessType.getExDesc(),
                excessType.getStatus(),
                excessType.getInactiveReason(),
                convertIntermediaryResponse(excessType.getIntermediary()),
                excessType.getCreatedBy(),
                excessType.getCreatedDateTime(),
                excessType.getModifiedBy(),
                excessType.getModifiedDateTime());
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
