package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.ConstructionDetailsItemsSaveRequest;
import lk.coop.dto.administration.request.ConstructionDetailsItemsUpdateRequest;
import lk.coop.dto.administration.response.ConstructionDetailsItemsResponse;
import lk.coop.entity.administration.ConstructionDetailsItems;
import lk.coop.entity.administration.InsuranceClass;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.ConstructionDetailsItemsRepository;
import lk.coop.service.administration.ConstructionDetailsItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConstructionDetailsItemsImpl implements ConstructionDetailsItemsService {

    private static final String INTER_ID = "1";

    @Autowired
    private ConstructionDetailsItemsRepository constructionDetailsItemsRepository;

    @Override
    public ConstructionDetailsItemsResponse save(ConstructionDetailsItemsSaveRequest constructionDetailsItemsSaveRequest) {
        try {
            return convertConstructionDetailsItems(this.constructionDetailsItemsRepository.save(convertReq(constructionDetailsItemsSaveRequest)));

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error Save Construction Details Items {}", constructionDetailsItemsSaveRequest.getItemName());
            return null;

        }
    }

    @Override
    public ConstructionDetailsItemsResponse update(ConstructionDetailsItemsUpdateRequest constructionDetailsItemsUpdateRequest) {
        try {
//            ConstructionDetailsItems constructionDetailsItems = this.constructionDetailsItemsRepository.getOne(constructionDetailsItemsUpdateRequest.getId(), INTER_ID);
            ConstructionDetailsItems constructionDetailsItems = this.constructionDetailsItemsRepository.getOne(Deleted.NO, constructionDetailsItemsUpdateRequest.getId());

            if (constructionDetailsItems != null) {
                ConstructionDetailsItems update = this.constructionDetailsItemsRepository.save(convert(constructionDetailsItemsUpdateRequest, constructionDetailsItems));
                return convertConstructionDetailsItems(update);
            } else {
                log.info("BOC Construction Details Items Not Found! {} ", constructionDetailsItemsUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Construction Details Items {} ", constructionDetailsItemsUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<ConstructionDetailsItemsResponse> getAll() {
//        return constructionDetailsItemsRepository.getAllList(Deleted.NO,INTER_ID).stream().
//                map(ConstructionDetailsItemsImpl::convertConstructionDetailsItems).collect(Collectors.toList());
        return constructionDetailsItemsRepository.findByIsDeleted(Deleted.NO).stream().
                map(ConstructionDetailsItemsImpl::convertConstructionDetailsItems).collect(Collectors.toList());

    }

    @Override
    public List<ConstructionDetailsItemsResponse> getAllActive() {
//        return constructionDetailsItemsRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().
//                map(ConstructionDetailsItemsImpl::convertConstructionDetailsItems).collect(Collectors.toList());
        return constructionDetailsItemsRepository.findByStatusAndIsDeleted(Status.ACTIVE, Deleted.NO).stream()
                .map(ConstructionDetailsItemsImpl::convertConstructionDetailsItems).collect(Collectors.toList());

    }

    @Override
    public ConstructionDetailsItemsResponse findById(String id) {
        try {
           // ConstructionDetailsItems constructionDetailsItems = constructionDetailsItemsRepository.getOne(id, INTER_ID);
            ConstructionDetailsItems constructionDetailsItems = constructionDetailsItemsRepository.getOne(id);
            if (constructionDetailsItems != null) {
                return convertConstructionDetailsItems(constructionDetailsItems);
            } else {
                log.info("NotFound constructionDetailsItems {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById constructionDetailsItems {}", id);
            return null;
        }
    }
    @Override
    public ConstructionDetailsItemsResponse delete(String id) {
        try {
          //  ConstructionDetailsItems response = constructionDetailsItemsRepository.getOne(id, INTER_ID);
            ConstructionDetailsItems response = constructionDetailsItemsRepository.getOne(Deleted.NO, id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                ConstructionDetailsItems responseDeleted = constructionDetailsItemsRepository.save(response);
                return convertConstructionDetailsItems(responseDeleted);
            } else {
                log.info("BOC  ConstructionDetailsItems Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC  ConstructionDetailsItems {} ", id);
            return null;
        }
    }
    /**
     * SAVE
     */
    private static ConstructionDetailsItems convertReq(ConstructionDetailsItemsSaveRequest constructionDetailsItemsSaveRequest) {
        ConstructionDetailsItems constructionDetailsItems = new ConstructionDetailsItems();
        constructionDetailsItems.setItemName(constructionDetailsItemsSaveRequest.getItemName());
        constructionDetailsItems.setItemDesc(constructionDetailsItemsSaveRequest.getItemDesc());
        constructionDetailsItems.setStatus(Status.ACTIVE);
        constructionDetailsItems.setInactiveReason(constructionDetailsItems.getInactiveReason());
        constructionDetailsItems.setIsDeleted(Deleted.NO);
        return constructionDetailsItems;
    }
    /**
     * UPDATE
     */
    private ConstructionDetailsItems convert(ConstructionDetailsItemsUpdateRequest constructionDetailsItemsUpdateRequest, ConstructionDetailsItems constructionDetailsItems) {
        constructionDetailsItems.setId(constructionDetailsItemsUpdateRequest.getId());
        constructionDetailsItems.setItemName(constructionDetailsItemsUpdateRequest.getItemName());
        constructionDetailsItems.setItemDesc(constructionDetailsItemsUpdateRequest.getItemDesc());
        //UPDATE
        constructionDetailsItems.setInactiveReason(((constructionDetailsItemsUpdateRequest.getStatus() == Status.ACTIVE) ? "" : constructionDetailsItemsUpdateRequest.getInactiveReson()));
        constructionDetailsItems.setStatus(constructionDetailsItemsUpdateRequest.getStatus());

        return constructionDetailsItems;
    }
    /**
     * VIEW
     */
    private static ConstructionDetailsItemsResponse convertConstructionDetailsItems(ConstructionDetailsItems constructionDetailsItems) {
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
}
