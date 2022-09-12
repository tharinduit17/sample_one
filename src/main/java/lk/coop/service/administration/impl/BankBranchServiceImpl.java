package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.BankBranchSaveRequest;
import lk.coop.dto.administration.request.BankBranchUpdateRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Intermediary;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.BankBranchRepository;
import lk.coop.service.administration.BankBranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankBranchServiceImpl implements BankBranchService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Autowired
    private BankBranchRepository bankBranchRepository;

    @Override
    public BankBranchResponse save(BankBranchSaveRequest bcBankBranchSaveRequest) {
        try {
            return convertBcBankBranch(this.bankBranchRepository.save(convertReq(bcBankBranchSaveRequest)));
        } catch (Exception e) {
            log.error("Error Save BankBranch {}", bcBankBranchSaveRequest.getName());
            return null;
        }
    }

    @Override
    public BankBranchResponse update(BankBranchUpdateRequest bankBranchUpdateRequest) {

        try {
            BankBranch bcBankBranch = this.bankBranchRepository.getOne(bankBranchUpdateRequest.getId(), INTER_ID);
            if (bcBankBranch != null) {
                BankBranch update = this.bankBranchRepository.save(convert(bankBranchUpdateRequest, bcBankBranch));
                return convertBcBankBranch(update);
            } else {
                log.info("BOC Bank Branch Not Found! {} ", bankBranchUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Bank Branch {} ", bankBranchUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<BankBranchResponse> getAllActive() {
        return bankBranchRepository.getActiveList(Status.ACTIVE, Deleted.NO,INTER_ID).stream().map(BankBranchServiceImpl::convertBcBankBranch).collect(Collectors.toList());
    }

    @Override
    public List<BankBranchResponse> getAll() {
        return bankBranchRepository.getAllList(Deleted.NO,INTER_ID).stream().map(BankBranchServiceImpl::convertBcBankBranch).collect(Collectors.toList());
    }

    @Override
    public BankBranchResponse findById(String id) {
        try {
            BankBranch bankBranch = bankBranchRepository.getOne(id, INTER_ID);
            if (bankBranch != null) {
                return convertBcBankBranch(bankBranch);
            } else {
                log.info("NotFound BankBranch {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById BankBranch {}", id);
            return null;
        }
    }

    @Override
    public BankBranchResponse delete(String id) {
        try {
            BankBranch response = bankBranchRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                BankBranch responseDeleted = bankBranchRepository.save(response);
                return convertBcBankBranch(responseDeleted);
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
    private static BankBranch convertReq(BankBranchSaveRequest bankBranchSaveRequest) {
        BankBranch bankBranch = new BankBranch();
        bankBranch.setCode(bankBranchSaveRequest.getCode());
        bankBranch.setName(bankBranchSaveRequest.getName());
        bankBranch.setArea(bankBranchSaveRequest.getArea());
        bankBranch.setEmail(bankBranchSaveRequest.getEmail());
        bankBranch.setGrade(bankBranchSaveRequest.getGrade());
        bankBranch.setProvince(bankBranchSaveRequest.getProvince());
        bankBranch.setAdminDistrict(bankBranchSaveRequest.getAdminDistrict());
        bankBranch.setIntermediary(convertIntermediary(bankBranchSaveRequest.getIntermediary()));
        bankBranch.setStatus(Status.ACTIVE);
        bankBranch.setIsDeleted(Deleted.NO);
        return bankBranch;
    }

    /**
     * UPDATE
     */
    private BankBranch convert(BankBranchUpdateRequest bcBankBranchUpdateRequest, BankBranch bcBankBranch) {
        bcBankBranch.setId(bcBankBranchUpdateRequest.getId());
        bcBankBranch.setCode(bcBankBranchUpdateRequest.getCode());
        bcBankBranch.setName(bcBankBranchUpdateRequest.getName());
        bcBankBranch.setArea(bcBankBranchUpdateRequest.getArea());
        bcBankBranch.setEmail(bcBankBranchUpdateRequest.getEmail());
        bcBankBranch.setGrade(bcBankBranchUpdateRequest.getGrade());
        bcBankBranch.setProvince(bcBankBranchUpdateRequest.getProvince());
        bcBankBranch.setAdminDistrict(bcBankBranchUpdateRequest.getAdminDistrict());
        //UPDATE
        bcBankBranch.setInactiveReason(((bcBankBranchUpdateRequest.getStatus() == Status.ACTIVE) ? "" : bcBankBranchUpdateRequest.getInactiveReason()));
        bcBankBranch.setStatus(bcBankBranchUpdateRequest.getStatus());
        bcBankBranch.setIntermediary(convertIntermediary(bcBankBranchUpdateRequest.getIntermediary()));

        return bcBankBranch;
    }

    /**
     * VIEW
     */
    private static BankBranchResponse convertBcBankBranch(BankBranch bcBankBranch) {
        if (bcBankBranch == null) {
            return null;
        }
        return new BankBranchResponse(bcBankBranch.getId(), bcBankBranch.getCode(), bcBankBranch.getName(), bcBankBranch.getGrade(),
                bcBankBranch.getProvince(), bcBankBranch.getEmail(), bcBankBranch.getArea(), bcBankBranch.getAdminDistrict(),
                bcBankBranch.getStatus(),convertIntermediaryResponse(bcBankBranch.getIntermediary()), bcBankBranch.getCreatedBy(),
                bcBankBranch.getCreatedDateTime(), bcBankBranch.getModifiedBy(),
                bcBankBranch.getModifiedDateTime());
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
