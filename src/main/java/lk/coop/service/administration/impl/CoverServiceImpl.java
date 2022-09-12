package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.CoverResponse;
import lk.coop.entity.administration.Cover;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.CoverRepository;
import lk.coop.service.administration.CoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoverServiceImpl implements CoverService {


    @Autowired
    private CoverRepository coverRepository;

    @Override
    public CoverResponse save(CoverSaveRequest coverSaveRequest) {
        try {
            return convertCover(this.coverRepository.save(convertReq(coverSaveRequest)));
        } catch (Exception e) {
            log.error("Error Save Cover {}", coverSaveRequest.getCo_code());
            return null;
        }
    }

    @Override
    public CoverResponse update(CoverUpdateRequest coverUpdateRequest) {

        try {
            Cover cover = this.coverRepository.getOne(coverUpdateRequest.getId());
            if (cover != null) {
                Cover update = this.coverRepository.save(convert(coverUpdateRequest, cover));
                return convertCover(update);
            } else {
                log.info("BOC Cover Not Found! {} ", coverUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Cover {} ", coverUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<CoverResponse> getAllActive() {
        return coverRepository.getActiveList(Status.ACTIVE, Deleted.NO).stream().map(CoverServiceImpl::convertCover).collect(Collectors.toList());
    }

    @Override
    public List<CoverResponse> getAll() {
        return coverRepository.getAllList(Deleted.NO).stream().map(CoverServiceImpl::convertCover).collect(Collectors.toList());
    }

    @Override
    public CoverResponse findById(String id) {
        try {
            Cover cover = coverRepository.getOne(id);
            if (cover != null) {
                return convertCover(cover);
            } else {
                log.info("NotFound Cover {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Cover {}", id);
            return null;
        }
    }

    @Override
    public CoverResponse delete(String id) {
        try {
            Cover response = coverRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                Cover responseDeleted = coverRepository.save(response);
                return convertCover(responseDeleted);
            } else {
                log.info("BOC Cover Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC Cover {} ", id);
            return null;
        }
    }

    /*** FUNCTION */

    /**
     * SAVE
     */
    private static Cover convertReq(CoverSaveRequest coverSaveRequest) {
            Cover cover = new Cover();
        cover.setCo_code(coverSaveRequest.getCo_code());
        cover.setCo_description(coverSaveRequest.getCo_description());
        cover.setStatus(Status.ACTIVE);
        cover.setIsDeleted(Deleted.NO);
        return cover;
    }

    /**
     * UPDATE
     */
    private Cover convert(CoverUpdateRequest coverUpdateRequest, Cover cover) {
        cover.setId(coverUpdateRequest.getId());
        cover.setCo_code(coverUpdateRequest.getCo_code());
        cover.setCo_description(coverUpdateRequest.getCo_description());
        //UPDATE
        cover.setInactiveReason(((coverUpdateRequest.getStatus() == Status.ACTIVE) ? "" : coverUpdateRequest.getInactiveReason()));
        cover.setStatus(coverUpdateRequest.getStatus());

        return cover;
    }


    /**
     * VIEW
     */
    private static CoverResponse convertCover(Cover cover) {
        if (cover == null) {
            return null;
        }
        return new CoverResponse(cover.getId(),
                cover.getCo_code(),
                cover.getCo_description(),
                cover.getStatus(),
                cover.getInactiveReason(),
                cover.getIsDeleted(),
                cover.getCreatedBy(),
                cover.getCreatedDateTime(), cover.getModifiedBy(),
                cover.getModifiedDateTime());
    }

}
