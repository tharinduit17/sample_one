package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.InsuranceProductResponse;
import lk.coop.dto.administration.response.InsuranceTypeResponse;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.dto.administration.response.PerilsResponse;
import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.entity.administration.InsuranceType;
import lk.coop.entity.administration.Intermediary;
import lk.coop.entity.administration.Perils;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.PerilsRepository;
import lk.coop.service.administration.PerilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PerilsTypeServiceImpl implements PerilsService {
    private static final String INTER_ID = "1";

    @Autowired
    private PerilsRepository perilsRepository;

    @Override
    public PerilsResponse save(PerilsSaveRequest perilsSaveRequest) {
        try {
             return convertPerilsType(this.perilsRepository.save(convertReq(perilsSaveRequest)));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Perils {}", perilsSaveRequest.getPName());
            return null;

        }
    }

    @Override
    public PerilsResponse update(PerilsUpdateRequest perilsUpdateRequest) {
        try {
            Perils perils = this.perilsRepository.getOne(perilsUpdateRequest.getId(), INTER_ID);
            if (perils != null) {
                Perils update = this.perilsRepository.save(convert(perilsUpdateRequest, perils));
                return convertPerilsType(update);
            } else {
                log.info("BOC Excess Type Not Found! {} ", perilsUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update BOC Excess Type {} ", perilsUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<PerilsResponse> getAll() {
        return perilsRepository.getAllList(Deleted.NO, INTER_ID).stream().map(PerilsTypeServiceImpl::convertPerilsType).collect(Collectors.toList());

    }

    @Override
    public List<PerilsResponse> getAllActive() {
        return perilsRepository.getActiveList(Status.ACTIVE, Deleted.NO, INTER_ID).stream().map(PerilsTypeServiceImpl::convertPerilsType).collect(Collectors.toList());

    }

    @Override
    public PerilsResponse findById(String id) {
        try {
            Perils perils = perilsRepository.getOne(id, INTER_ID);
            if (perils != null) {
                return convertPerilsType(perils);
            } else {
                log.info("NotFound Perils {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Perils {}", id);
            return null;
        }
    }

    @Override
    public PerilsResponse delete(String id) {
        try {
            Perils response = perilsRepository.getOne(id, INTER_ID);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                Perils responseDeleted = perilsRepository.save(response);
                return convertPerilsType(responseDeleted);
            } else {
                log.info("BOC  Perils Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete BOC  Perils {} ", id);
            return null;
        }
    }
    /*** FUNCTION */

    /**
     * SAVE
     */
    private static Perils convertReq(PerilsSaveRequest perilsSaveRequest) {
        Perils perils = new Perils();
        perils.setPName(perilsSaveRequest.getPName());
        perils.setPDsesc(perilsSaveRequest.getPDsesc());
        perils.setCode(perilsSaveRequest.getCode());
        perils.setInactiveReason(perils.getInactiveReason());
        perils.setIntermediary(convertIntermediary(perilsSaveRequest.getIntermediary()));
        perils.setInsurance(convertInsuranceType(perilsSaveRequest.getInsurance()));
        perils.setInsuranceProduct(convertInsuranceProduct(perilsSaveRequest.getInsuranceProduct()));
        perils.setStatus(Status.ACTIVE);
        perils.setIsDeleted(Deleted.NO);
        return perils;
    }

    /**
     * UPDATE
     */
    private Perils convert(PerilsUpdateRequest perilsUpdateRequest, Perils perils) {
        perils.setId(perilsUpdateRequest.getId());
        perils.setPName(perilsUpdateRequest.getPName());
        perils.setPDsesc(perilsUpdateRequest.getPDsesc());
        perils.setCode(perilsUpdateRequest.getCode());
        //UPDATE
        perils.setInactiveReason(((perilsUpdateRequest.getStatus() == Status.ACTIVE) ? "" : perilsUpdateRequest.getInactiveReason()));
        perils.setStatus(perilsUpdateRequest.getStatus());
        perils.setIntermediary(convertIntermediary(perilsUpdateRequest.getIntermediary()));
        perils.setInsuranceProduct(convertInsuranceProduct(perilsUpdateRequest.getInsuranceProduct()));
        perils.setInsurance(convertInsuranceType(perilsUpdateRequest.getInsurance()));

        return perils;
    }

    /**
     * VIEW
     */
    private static PerilsResponse convertPerilsType(Perils perils) {
        if (perils == null) {
            return null;
        }
        return new PerilsResponse(
                perils.getId(),
                perils.getCode(),
                perils.getPName(),
                perils.getPDsesc(),
                perils.getStatus(),
                perils.getInactiveReason(),
                convertIntermediaryResponse(perils.getIntermediary()),
                convertInsuranceTypeResponse(perils.getInsurance()),
                convertInsuranceProductResponse(perils.getInsuranceProduct()),
                perils.getCreatedBy(),
                perils.getCreatedDateTime(),
                perils.getModifiedBy(),
                perils.getModifiedDateTime());
    }

    private static InsuranceTypeResponse convertInsuranceTypeResponse(InsuranceType insuranceType) {
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

    private static InsuranceProductResponse convertInsuranceProductResponse(InsuranceProduct insuranceProduct) {
        if (insuranceProduct == null) {
            return null;
        }
        return new InsuranceProductResponse(
                insuranceProduct.getId(),
                insuranceProduct.getProCode(),
                insuranceProduct.getProName(),
                insuranceProduct.getProDescription(),
                insuranceProduct.getStatus(),
                convertIntermediaryResponse(insuranceProduct.getIntermediary()),
                insuranceProduct.getCreatedBy(),
                insuranceProduct.getCreatedDateTime(),
                insuranceProduct.getModifiedBy(),
                insuranceProduct.getModifiedDateTime(),
                insuranceProduct.getInactiveReason()
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
    private static InsuranceType convertInsuranceType(InsuranceTypeRequest insuranceTypeRequest) {
        if (insuranceTypeRequest == null) {
            return null;
        }
        InsuranceType insurance = new InsuranceType();
        insurance.setId(insuranceTypeRequest.getId());

        return insurance;
    }

    private static InsuranceProduct convertInsuranceProduct(InsuranceProductRequest insuranceProductRequest){
        if (insuranceProductRequest==null){
            return null;
        }
        InsuranceProduct insuranceProduct = new InsuranceProduct();
        insuranceProduct.setId(insuranceProductRequest.getId());
        return insuranceProduct;
    }
}

