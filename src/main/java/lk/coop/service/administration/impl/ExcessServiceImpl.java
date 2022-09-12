package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.ExcessSaveRequest;
import lk.coop.dto.administration.request.ExcessTypeRequest;
import lk.coop.dto.administration.request.ExcessUpdateRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.administration.response.ExcessResponse;
import lk.coop.dto.administration.response.ExcessTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.Excess;
import lk.coop.entity.administration.ExcessType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.ExcessRepository;
import lk.coop.service.administration.ExcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcessServiceImpl implements ExcessService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Autowired
    private ExcessRepository excessRepository;


    @Override
    public ExcessResponse save(ExcessSaveRequest excessSaveRequest) {
        try {
            return convertExcess(this.excessRepository.save(convertReq(excessSaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Excess {}", excessSaveRequest.getExcess());
            return null;
        }
    }

    @Override
    public ExcessResponse update(ExcessUpdateRequest excessUpdateRequest) {
        try {
            Excess excess = this.excessRepository.getOne(excessUpdateRequest.getId(), INTER_ID);
            if (excess != null) {
                Excess update = this.excessRepository.save(convert(excessUpdateRequest, excess));
                return convertExcess(update);
            } else {
                log.info("Excess Not Found! {} ", excessUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update Excess {} ", excessUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<ExcessResponse> getAll() {
        return excessRepository.getAllList(Deleted.NO, INTER_ID).stream().map(ExcessServiceImpl::convertExcess).collect(Collectors.toList());
    }

    @Override
    public List<ExcessResponse> getAllActive() {
        return excessRepository.getActiveList(Status.ACTIVE, Deleted.NO, INTER_ID).stream().map(ExcessServiceImpl::convertExcess).collect(Collectors.toList());
    }

    @Override
    public ExcessResponse findById(String id) {
        try {
            Excess excess = excessRepository.getOne(id, INTER_ID);
            if (excess != null) {
                return convertExcess(excess);
            } else {
                log.info("NotFound Excess {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Excess {}", id);
            return null;
        }
    }

    @Override
    public ExcessResponse delete(String id) {
        try {
            Excess response = excessRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                Excess responseDeleted = excessRepository.save(response);
                return convertExcess(responseDeleted);
            } else {
                log.info("Excess Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete Excess {} ", id);
            return null;
        }
    }

/*** FUNCTION */

    /**
     * SAVE
     */
    private static Excess convertReq(ExcessSaveRequest excessSaveRequest) {
        Excess excess = new Excess();
        excess.setYear(excessSaveRequest.getYear());
        excess.setExcess(excessSaveRequest.getExcess());
        excess.setExDesc(excessSaveRequest.getExDesc());
        excess.setIntermediary(convertIntermediary(excessSaveRequest.getIntermediary()));
        excess.setExcessType(convertExcessType(excessSaveRequest.getExcessType()));
        excess.setStatus(Status.ACTIVE);
        excess.setIsDeleted(Deleted.NO);
        return excess;
    }

    /**
     * UPDATE
     */
    private Excess convert(ExcessUpdateRequest excessUpdateRequest, Excess excess) {
        excess.setId(excessUpdateRequest.getId());
        excess.setYear(excessUpdateRequest.getYear());
        excess.setExcess(excessUpdateRequest.getExcess());
        excess.setExDesc(excessUpdateRequest.getExDesc());
        //UPDATE
        excess.setInactiveReason(((excessUpdateRequest.getStatus() == Status.ACTIVE) ? "" : excessUpdateRequest.getInactiveReason()));
        excess.setStatus(excessUpdateRequest.getStatus());
        excess.setIntermediary(convertIntermediary(excessUpdateRequest.getIntermediary()));
        excess.setExcessType(convertExcessType(excessUpdateRequest.getExcessType()));

        return excess;
    }

    /**
     * VIEW
     */
    private static ExcessResponse convertExcess(Excess excess) {
        if (excess == null) {
            return null;
        }
        return new ExcessResponse(excess.getId(), excess.getYear(), excess.getExcess(), excess.getExDesc(),
                convertIntermediaryResponse(excess.getIntermediary()), convertExcessTypeResponse(excess.getExcessType()),
                excess.getStatus(), excess.getInactiveReason(), excess.getCreatedBy(), excess.getCreatedDateTime(),
                excess.getModifiedBy(), excess.getModifiedDateTime());
    }

    private static IntermediaryResponse convertIntermediaryResponse(Intermediary intermediary) {
        if (intermediary == null) {
            return null;
        }
        return new IntermediaryResponse(intermediary.getId(), intermediary.getName(), intermediary.getDescription(),
                intermediary.getEmail(), intermediary.getStatus(), intermediary.getCreatedBy(), intermediary.getCreatedDateTime(),
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

    private static ExcessTypeResponse convertExcessTypeResponse(ExcessType excessType) {
        if (excessType == null) {
            return null;
        }
        return new ExcessTypeResponse(excessType.getId(), excessType.getExType(), excessType.getExDesc(), excessType.getStatus(),
                excessType.getInactiveReason(), convertIntermediaryResponse(excessType.getIntermediary()), excessType.getCreatedBy(),
                excessType.getCreatedDateTime(), excessType.getModifiedBy(), excessType.getModifiedDateTime());
    }

    private static ExcessType convertExcessType(ExcessTypeRequest excessTypeRequest) {
        if (excessTypeRequest == null) {
            return null;
        }
        ExcessType excessType = new ExcessType();
        excessType.setId(excessTypeRequest.getId());

        return excessType;
    }
}
