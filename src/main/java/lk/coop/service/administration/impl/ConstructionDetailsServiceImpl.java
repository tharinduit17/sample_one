package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.ConstructionDetailsItemsResponse;
import lk.coop.dto.administration.response.ConstructionDetailsResponse;
import lk.coop.dto.administration.response.DocumentTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.*;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.ConstructionDetailsRepository;
import lk.coop.service.administration.ConstructionDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConstructionDetailsServiceImpl implements ConstructionDetailsService {

    private static final String INTER_ID = "1";

    @Autowired
    private ConstructionDetailsRepository constructionDetailsRepository;

    @Override
    public ConstructionDetailsResponse save(ConstructionDetailsSaveRequest constructionDetailsSaveRequest) {
        try {
            return convertConstructionDetails(this.constructionDetailsRepository.save(convertReq(constructionDetailsSaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Construction Details {}", constructionDetailsSaveRequest.getTypeName());
            return null;
        }
    }

    @Override
    public ConstructionDetailsResponse update(ConstructionDetailsUpdateRequest constructionDetailsUpdateRequest) {

//        try {
//            ConstructionDetails constructionDetails = this.constructionDetailsRepository.findById(constructionDetailsUpdateRequest.getId());
//            if (constructionDetails != null) {
//                ConstructionDetails update = this.constructionDetailsRepository.save(convert(constructionDetailsUpdateRequest, constructionDetails));
//                return convertConstructionDetails(update);
//            } else {
//                log.info("BOC Construction Details Not Found! {} ", constructionDetailsUpdateRequest.getId());
//                return null;
//            }
//        } catch (Exception e) {
//            log.error("Error Update BOC Construction Details {} ", constructionDetailsUpdateRequest.getId());
//            return null;
//        }
//
//        return null;

        try {
            return convertConstructionDetails(this.constructionDetailsRepository.save(convert(constructionDetailsUpdateRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Construction Details {}", constructionDetailsUpdateRequest.getTypeName());
            return null;
        }
    }

    @Override
    public List<ConstructionDetailsResponse> getAll() {

        return constructionDetailsRepository.findByIsDeleted(Deleted.NO).stream().map(ConstructionDetailsServiceImpl::convertConstructionDetails).collect(Collectors.toList());

//        return constructionDetailsRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().map(ConstructionDetailsServiceImpl::convertonstructionDetails).collect(Collectors.toList());

    }

    @Override
    public List<ConstructionDetailsResponse> getAllActive() {
        return constructionDetailsRepository.findByStatusAndIsDeleted(Status.ACTIVE, Deleted.NO).stream().map(ConstructionDetailsServiceImpl::convertConstructionDetails).collect(Collectors.toList());
    }

    @Override
    public ConstructionDetailsResponse getById(String id) {

        try {

            return constructionDetailsRepository.findById(id).map(ConstructionDetailsServiceImpl::convertConstructionDetails).orElse(null);

//            ConstructionDetails constructionDetails = constructionDetailsRepository.findById(id);
//            if (constructionDetails != null) {
//                return convertConstructionDetails(constructionDetails);
//            } else {
//                log.info("NotFound BankBranch {}", id);
//                return null;
//            }
        } catch (Exception e) {
            log.error("Error findById BankBranch {}", id);
            return null;
        }

//        return null;
    }

    @Override
    public ConstructionDetailsResponse delete(String id) {
        try {
            ConstructionDetails response = constructionDetailsRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                ConstructionDetails responseDeleted = constructionDetailsRepository.save(response);
                return convertConstructionDetails(responseDeleted);
            } else {
                log.info("BOC Construction Details Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC Construction Details {} ", id);
            return null;
        }

    }
/*** FUNCTION */

    /**
     * SAVE
     */
    private static ConstructionDetails convertReq(ConstructionDetailsSaveRequest constructionDetailsSaveRequest) {

        ConstructionDetails constructionDetails = new ConstructionDetails();

        constructionDetails.setTypeName(constructionDetailsSaveRequest.getTypeName());
        constructionDetails.setTypeDescription(constructionDetailsSaveRequest.getTypeDescription());
        constructionDetails.setConstructionDetailsItems(constructionDetailsSaveRequest.getConstructionDetailsItems().stream().map(constructionDetailItem -> convertConstructionDetailsItems(constructionDetailItem)).collect(Collectors.toList()));
        constructionDetails.setStatus(Status.ACTIVE);
        constructionDetails.setIsDeleted(Deleted.NO);

        constructionDetails.getConstructionDetailsItems().forEach(eachItem -> eachItem.setConstructionDetail(constructionDetails));
        constructionDetails.setInactiveReason(constructionDetails.getInactiveReason());
        return constructionDetails;
    }

    /**
     * UPDATE
     */
    private ConstructionDetails convert(ConstructionDetailsUpdateRequest constructionDetailsUpdateRequest) {

        ConstructionDetails constructionDetails = new ConstructionDetails();

        constructionDetails.setId(constructionDetailsUpdateRequest.getId());
        constructionDetails.setTypeName(constructionDetailsUpdateRequest.getTypeName());
        constructionDetails.setTypeDescription(constructionDetailsUpdateRequest.getTypeDescription());
        constructionDetails.setStatus(constructionDetailsUpdateRequest.getStatus());
        constructionDetails.setInactiveReason(((constructionDetailsUpdateRequest.getStatus() == Status.ACTIVE) ? "" : constructionDetailsUpdateRequest.getInactiveReason()));
        constructionDetails.setIsDeleted(Deleted.NO);
        constructionDetails.setConstructionDetailsItems(constructionDetailsUpdateRequest.getConstructionDetailsItems().stream().map(constructionDetailItem -> convertConstructionDetailsItemsUpdate(constructionDetailItem)).collect(Collectors.toList()));

        constructionDetails.getConstructionDetailsItems().forEach(eachItem -> eachItem.setConstructionDetail(constructionDetails));

        return constructionDetails;

    }

    /**
     * VIEW
     */
    private static ConstructionDetailsResponse convertConstructionDetails(ConstructionDetails constructionDetails) {

        if (constructionDetails == null) {
            return null;
        }

        return new ConstructionDetailsResponse(

                constructionDetails.getId(),
                constructionDetails.getTypeName(),
                constructionDetails.getTypeDescription(),
                constructionDetails.getStatus(),
//                convertConstructionDetailsItemsResponse(constructionDetails.getConstructionDetailsItems()),
                constructionDetails.getConstructionDetailsItems().stream().map(conDetailItem ->
                        convertConstructionDetailsItemsResponse(conDetailItem)).collect(Collectors.toList()),
                // convertConstructionDetailsItemsResponse(constructionDetails.getConstructionDetailsItems()),

                constructionDetails.getCreatedBy(),
                constructionDetails.getCreatedDateTime(),
                constructionDetails.getModifiedBy(),
                constructionDetails.getModifiedDateTime(),
                constructionDetails.getInactiveReason());
    }


    private static ConstructionDetailsItemsResponse convertConstructionDetailsItemsResponse(ConstructionDetailsItems constructionDetailsItems) {

        if (constructionDetailsItems == null) {
            return null;
        }

        return new ConstructionDetailsItemsResponse(
                constructionDetailsItems.getId(),
                constructionDetailsItems.getItemName(),
                constructionDetailsItems.getItemDesc(),
                constructionDetailsItems.getStatus(),
                constructionDetailsItems.getInactiveReason(),
                constructionDetailsItems.getCreatedBy(),
                constructionDetailsItems.getCreatedDateTime(),
                constructionDetailsItems.getModifiedBy(),
                constructionDetailsItems.getModifiedDateTime());
    }

    private static ConstructionDetailsItems convertConstructionDetailsItems(ConstructionDetailsItemsSaveRequest constructionDetailsItemsSaveRequest) {

        if (constructionDetailsItemsSaveRequest == null) {
            return null;
        }

        ConstructionDetailsItems constructionDetailsItem = new ConstructionDetailsItems();

        constructionDetailsItem.setItemName(constructionDetailsItemsSaveRequest.getItemName());
        constructionDetailsItem.setItemDesc(constructionDetailsItemsSaveRequest.getItemDesc());
        constructionDetailsItem.setStatus(Status.ACTIVE);
        constructionDetailsItem.setIsDeleted(Deleted.NO);

        return constructionDetailsItem;
    }

    private static ConstructionDetailsItems convertConstructionDetailsItemsUpdate(ConstructionDetailsItemsUpdateRequest constructionDetailsItemsUpdateRequest) {

        if (constructionDetailsItemsUpdateRequest == null) {
            return null;
        }

        ConstructionDetailsItems constructionDetailsItem = new ConstructionDetailsItems();

        if (constructionDetailsItemsUpdateRequest.getId() != null) {

            constructionDetailsItem.setId(constructionDetailsItemsUpdateRequest.getId());
            constructionDetailsItem.setItemName(constructionDetailsItemsUpdateRequest.getItemName());
            constructionDetailsItem.setItemDesc(constructionDetailsItemsUpdateRequest.getItemDesc());
            constructionDetailsItem.setStatus(constructionDetailsItemsUpdateRequest.getStatus());
            constructionDetailsItem.setIsDeleted(Deleted.NO);

            return constructionDetailsItem;

        } else {

            constructionDetailsItem.setItemName(constructionDetailsItemsUpdateRequest.getItemName());
            constructionDetailsItem.setItemDesc(constructionDetailsItemsUpdateRequest.getItemDesc());
            constructionDetailsItem.setStatus(Status.ACTIVE);
            constructionDetailsItem.setIsDeleted(Deleted.NO);

            return constructionDetailsItem;
        }

    }

}


