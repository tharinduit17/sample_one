package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.dto.administration.response.PeriodCoverResponse;
import lk.coop.entity.administration.Intermediary;
import lk.coop.entity.administration.PeriodCover;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.PeriodCoverRepository;
import lk.coop.service.administration.PeriodCoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PeriodCoverServiceImpl implements PeriodCoverService {
    private static final String INTER_ID = "1";

    @Autowired
    private PeriodCoverRepository periodCoverRepository;
    @Override
    public PeriodCoverResponse save(PeriodCoverSaveRequest periodCoverSaveRequest) {
        try {
            return convertPeriodCover(this.periodCoverRepository.save(convertReq(periodCoverSaveRequest)));

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error Save Period Cover {}", periodCoverSaveRequest.getYear());
            return null;

        }
    }

    @Override
    public PeriodCoverResponse update(PeriodCoverUpdateRequest periodCoverUpdateRequest) {
        try {
            PeriodCover periodCover = this.periodCoverRepository.getOne(periodCoverUpdateRequest.getId(), INTER_ID);
            if (periodCover != null) {
                PeriodCover update = this.periodCoverRepository.save(convert(periodCoverUpdateRequest, periodCover));
                return convertPeriodCover(update);
            } else {
                log.info("BOC Period Cover Not Found! {} ", periodCoverUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Period Cover Type {} ", periodCoverUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<PeriodCoverResponse> getAll() {
        return periodCoverRepository.getAllList(Deleted.NO,INTER_ID).stream().map(PeriodCoverServiceImpl::convertPeriodCover).collect(Collectors.toList());

    }

    @Override
    public List<PeriodCoverResponse> getAllActive() {
        return periodCoverRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().map(PeriodCoverServiceImpl::convertPeriodCover).collect(Collectors.toList());

    }

    @Override
    public PeriodCoverResponse findById(String id) {
        try {
            PeriodCover periodCover = periodCoverRepository.getOne(id, INTER_ID);
            if (periodCover != null) {
                return convertPeriodCover(periodCover);
            } else {
                log.info("NotFound periodCover {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById periodCover {}", id);
            return null;
        }
    }

    @Override
    public PeriodCoverResponse delete(String id) {
        try {
            PeriodCover response = periodCoverRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                PeriodCover responseDeleted = periodCoverRepository.save(response);
                return convertPeriodCover(responseDeleted);
            } else {
                log.info("BOC  periodCover Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC  periodCover {} ", id);
            return null;
        }
    }
    /*** FUNCTION */

    /**
     * SAVE
     */
    private static PeriodCover convertReq(PeriodCoverSaveRequest periodCoverSaveRequest) {
        PeriodCover periodCover = new PeriodCover();
        periodCover.setYear(periodCoverSaveRequest.getYear());
        periodCover.setDescription(periodCoverSaveRequest.getDescription());
        periodCover.setInactiveReason(periodCover.getInactiveReason());
        periodCover.setIntermediary(convertIntermediary(periodCoverSaveRequest.getIntermediary()));
        periodCover.setStatus(Status.ACTIVE);
        periodCover.setIsDeleted(Deleted.NO);
        return periodCover;
    }

    /**
     * UPDATE
     */
    private PeriodCover convert(PeriodCoverUpdateRequest periodCoverUpdateRequest, PeriodCover periodCover) {
        periodCover.setId(periodCoverUpdateRequest.getId());
        periodCover.setDescription(periodCoverUpdateRequest.getDescription());
        periodCover.setYear(periodCoverUpdateRequest.getYear());
        //UPDATE
        periodCover.setInactiveReason(((periodCoverUpdateRequest.getStatus() == Status.ACTIVE) ? "" : periodCoverUpdateRequest.getInactiveReson()));
        periodCover.setStatus(periodCoverUpdateRequest.getStatus());
        periodCover.setIntermediary(convertIntermediary(periodCoverUpdateRequest.getIntermediary()));

        return periodCover;
    }
    /**
     * VIEW
     */
    private static PeriodCoverResponse convertPeriodCover (PeriodCover periodCover) {
        if (periodCover == null) {
            return null;
        }
        return new PeriodCoverResponse(
                periodCover.getId(),
                periodCover.getYear(),
                periodCover.getDescription(),
                periodCover.getInactiveReason(),
                convertIntermediaryResponse(periodCover.getIntermediary()),
                periodCover.getStatus(),
                periodCover.getCreatedBy(),
                periodCover.getCreatedDateTime(),
                periodCover.getModifiedBy(),
                periodCover.getModifiedDateTime());
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
