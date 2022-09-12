package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.TitleTypeSaveRequest;
import lk.coop.dto.administration.request.TitleTypeUpdateRequest;
import lk.coop.dto.administration.response.TitleTypeResponse;
import lk.coop.entity.administration.TitleType;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.TitleTypeRepository;
import lk.coop.service.administration.TitleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TitleTypeServiceImpl implements TitleTypeService {

    @Autowired
    private TitleTypeRepository titleTypeRepository;


    @Override
    public TitleTypeResponse save(TitleTypeSaveRequest titleTypeSaveRequest) {

        try {
            return convertTitleType(this.titleTypeRepository.save(convertReq(titleTypeSaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save TitleType {}", titleTypeSaveRequest.getTitleType());
            return null;
        }
    }

    @Override
    public TitleTypeResponse update(TitleTypeUpdateRequest titleTypeUpdateRequest) {

        try {
            TitleType titleType = this.titleTypeRepository.getOne(titleTypeUpdateRequest.getId());
            if (titleType != null) {
                TitleType update = this.titleTypeRepository.save(covert(titleTypeUpdateRequest, titleType));
                return convertTitleType((update));
            } else {
                log.info("Title Type Not Found! {} ", titleTypeUpdateRequest.getId());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Update Title Type {} ", titleTypeUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<TitleTypeResponse> getAllActive() {
        return titleTypeRepository.getActiveList(Status.ACTIVE, Deleted.NO).stream().map(TitleTypeServiceImpl::convertTitleType).collect(Collectors.toList());
    }


    @Override
    public TitleTypeResponse findById(String id) {

        try {
            TitleType titleType = titleTypeRepository.getOne(id);
            if (titleType != null) {
                return convertTitleType(titleType);
            } else {
                log.info("NotFound TitleType {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById TitleType {}", id);
            return null;
        }
    }

    @Override
    public TitleTypeResponse delete(String id) {

        try {
            TitleType response = titleTypeRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                TitleType responseDeleted = titleTypeRepository.save(response);
                return convertTitleType(responseDeleted);
            } else {
                log.info("TitleType Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete TitleType {} ", id);
            return null;
        }
    }

    @Override
    public List<TitleTypeResponse> getAll() {
        return titleTypeRepository.getAllList(Deleted.NO).stream().map(TitleTypeServiceImpl::convertTitleType).collect(Collectors.toList());
    }

    /*** FUNCTION */

    /**
     * SAVE
     */
    private static TitleType convertReq(TitleTypeSaveRequest titleTypeSaveRequest) {

        TitleType titleType = new TitleType();
        titleType.setTitleType(titleTypeSaveRequest.getTitleType());
        titleType.setTitleDesc(titleTypeSaveRequest.getTitleDesc());
        titleType.setStatus(Status.ACTIVE);
        titleType.setIsDeleted(Deleted.NO);

        return titleType;
    }

    /**
     * UPDATE
     */

    private TitleType covert(TitleTypeUpdateRequest titleTypeUpdateRequest, TitleType titleType) {
        titleType.setId(titleTypeUpdateRequest.getId());
        titleType.setTitleType(titleTypeUpdateRequest.getTitleType());
        titleType.setTitleDesc(titleTypeUpdateRequest.getTitleDesc());
        //UPDATE
        titleType.setInactiveReason(((titleTypeUpdateRequest.getStatus() == Status.ACTIVE) ? "" : titleTypeUpdateRequest.getInactiveReason()));
        titleType.setStatus(titleTypeUpdateRequest.getStatus());

        return titleType;
    }

    /**
     * VIEW
     */
    private static TitleTypeResponse convertTitleType(TitleType titleType) {

        if (titleType == null) {
            return null;
        }
        if (titleType == null) {
            return null;
        }
        return new TitleTypeResponse(
                titleType.getId(),
                titleType.getTitleType(),
                titleType.getTitleDesc(),
                titleType.getStatus(),
                titleType.getInactiveReason(),
                titleType.getCreatedBy(),
                titleType.getCreatedDateTime(),
                titleType.getModifiedBy(),
                titleType.getModifiedDateTime());
    }
}
