package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.InsuranceTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.InsuranceType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.InsuranceTypeRepository;
import lk.coop.repository.administration.IntermediaryRepository;
import lk.coop.service.administration.InsuranceTypeService;
import lk.coop.service.administration.IntermediaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class IntermediaryServiceImpl implements IntermediaryService {

//    @Value("${intermediary.id}")
//    private String INTER_ID;

    @Autowired
    private IntermediaryRepository intermediaryRepository;

    @Override
    public IntermediaryResponse save(IntermediarySaveRequest intermediarySaveRequest) {


        try {
            return convertIntermediary(this.intermediaryRepository.save(convertReq(intermediarySaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Intermediary {}", intermediarySaveRequest.getName());
            return null;
        }

    }

    @Override
    public IntermediaryResponse update(IntermediaryUpdateRequest intermediaryUpdateRequest) {

        try {
            Intermediary intermediary = this.intermediaryRepository.getOne(intermediaryUpdateRequest.getId());
            if (intermediary != null) {
                Intermediary update = this.intermediaryRepository.save(convert(intermediaryUpdateRequest, intermediary));
                return convertIntermediary(update);
            } else {
                log.info("Insurance Type Not Found! {} ", intermediaryUpdateRequest.getId());
                return null;
            }

        } catch (Exception e) {
            log.error("Error Update Insurance Type {} ", intermediaryUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<IntermediaryResponse> getAll() {
        return intermediaryRepository.getAllList(Deleted.NO).stream().map(IntermediaryServiceImpl::convertIntermediary).collect(Collectors.toList());
    }

    @Override
    public List<IntermediaryResponse> getAllActive() {
        return intermediaryRepository.getActiveList(Status.ACTIVE, Deleted.NO).stream().map(IntermediaryServiceImpl::convertIntermediary).collect(Collectors.toList());
    }

    @Override
    public IntermediaryResponse findById(String id) {
        try {
            Intermediary intermediary = intermediaryRepository.getOne(id);
            if (intermediary != null) {
                return convertIntermediary(intermediary);
            } else {
                log.info("NotFound Intermediary {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Intermediary {}", id);
            return null;
        }
    }

    @Override
    public IntermediaryResponse delete(String id) {

        try {
            Intermediary response = intermediaryRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                Intermediary responseDeleted = intermediaryRepository.save(response);
                return convertIntermediary(responseDeleted);
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
    private static Intermediary convertReq(IntermediarySaveRequest intermediarySaveRequest) {

        Intermediary intermediary = new Intermediary();

        intermediary.setName(intermediarySaveRequest.getName());
        intermediary.setDescription(intermediarySaveRequest.getDescription());
        intermediary.setEmail(intermediarySaveRequest.getEmail());
        intermediary.setStatus(Status.ACTIVE);
        intermediary.setIsDeleted(Deleted.NO);
        return intermediary;
    }

    /**
     * UPDATE
     */

    private Intermediary convert(IntermediaryUpdateRequest intermediaryUpdateRequest, Intermediary intermediary) {
        intermediary.setId(intermediaryUpdateRequest.getId());
        intermediary.setDescription(intermediaryUpdateRequest.getDescription());
        intermediary.setEmail(intermediaryUpdateRequest.getEmail());
        //UPDATE
        intermediary.setInactiveReason(((intermediaryUpdateRequest.getStatus() == Status.ACTIVE) ? "" : intermediaryUpdateRequest.getInactiveReason()));
        intermediary.setStatus(intermediaryUpdateRequest.getStatus());

        return intermediary;
    }

    /**
     * VIEW
     */
    private static IntermediaryResponse convertIntermediary(Intermediary intermediary) {

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
}

