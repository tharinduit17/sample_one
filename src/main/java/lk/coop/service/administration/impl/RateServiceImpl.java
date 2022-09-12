package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.InsuranceProductRequest;
import lk.coop.dto.administration.request.RateSaveRequest;
import lk.coop.dto.administration.request.RateUpdateRequest;
import lk.coop.dto.administration.response.RateResponse;
import lk.coop.dto.administration.response.RateSummaryResponse;
import lk.coop.dto.non_motor.response.ProductResponse;
import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.entity.administration.Rate;
import lk.coop.enums.Deleted;
import lk.coop.enums.Status;
import lk.coop.repository.administration.RateRepository;
import lk.coop.service.administration.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RateServiceImpl implements RateService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Autowired
    private RateRepository rateRepository;


    @Override
    public RateResponse save(RateSaveRequest rateSaveRequest) {
        try {
            return convertRate(this.rateRepository.save(convertReq(rateSaveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Rate {}", rateSaveRequest.getRate());
            return null;
        }
    }

    @Override
    public RateResponse update(RateUpdateRequest rateUpdateRequest) {
        try {
            Rate rate = this.rateRepository.getOne(rateUpdateRequest.getId());
            if (rate != null) {
                Rate update = this.rateRepository.save(convert(rateUpdateRequest, rate));
                return convertRate(update);
            } else {
                log.info("Rate Not Found! {} ", rateUpdateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update Rate {} ", rateUpdateRequest.getId());
            return null;
        }
    }

    @Override
    public List<RateResponse> getAll() {
        return rateRepository.getAllList(Deleted.NO, INTER_ID).stream().map(RateServiceImpl::convertRate).collect(Collectors.toList());
    }

    @Override
    public List<RateResponse> getAllActive() {
        return rateRepository.getActiveList(Status.ACTIVE, Deleted.NO, INTER_ID).stream().map(RateServiceImpl::convertRate).collect(Collectors.toList());
    }

    @Override
    public List<RateSummaryResponse> getRateRange(String productId,Integer to) {
        return rateRepository.getRateList(Status.ACTIVE,productId,1,to).stream().map(RateServiceImpl::convertRateSummary).collect(Collectors.toList());
    }

    @Override
    public RateResponse findById(String id) {
        try {
            Rate rate = rateRepository.getOne(id);
            if (rate != null) {
                return convertRate(rate);
            } else {
                log.info("NotFound Rate {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById Rate {}", id);
            return null;
        }
    }

    @Override
    public RateResponse delete(String id) {
        try {
            Rate response = rateRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                Rate responseDeleted = rateRepository.save(response);
                return convertRate(responseDeleted);
            } else {
                log.info("Rate Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete Rate {} ", id);
            return null;
        }
    }

/*** FUNCTION */

    /**
     * SAVE
     */
    private static Rate convertReq(RateSaveRequest rateSaveRequest) {
        Rate rate = new Rate();
        rate.setYear(rateSaveRequest.getYear());
        rate.setRate(rateSaveRequest.getRate());
        rate.setRateDesc(rateSaveRequest.getRateDesc());
        rate.setProduct(convertInsuranceProduct(rateSaveRequest.getProduct()));
        rate.setStatus(Status.ACTIVE);
        rate.setIsDeleted(Deleted.NO);
        return rate;
    }

    /**
     * UPDATE
     */
    private Rate convert(RateUpdateRequest rateUpdateRequest, Rate rate) {
        rate.setId(rateUpdateRequest.getId());
        rate.setYear(rateUpdateRequest.getYear());
        rate.setRate(rateUpdateRequest.getRate());
        rate.setRateDesc(rateUpdateRequest.getRateDesc());
        rate.setProduct(convertInsuranceProduct(rateUpdateRequest.getProduct()));
        //UPDATE
        rate.setInactiveReason(((rateUpdateRequest.getStatus() == Status.ACTIVE) ? "" : rateUpdateRequest.getInactiveReason()));
        rate.setStatus(rateUpdateRequest.getStatus());

        return rate;
    }

    private static InsuranceProduct convertInsuranceProduct(InsuranceProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }
        InsuranceProduct insuranceProduct = new InsuranceProduct();
        insuranceProduct.setId(productRequest.getId());
        return insuranceProduct;
    }

    /**
     * VIEW
     */
    private static RateResponse convertRate(Rate rate) {
        if (rate == null) {
            return null;
        }
        return new RateResponse(rate.getId(), rate.getYear(), rate.getRate(), rate.getRateDesc(),
                convertProductResponse(rate.getProduct()), rate.getStatus(), rate.getInactiveReason(), rate.getCreatedBy(), rate.getCreatedDateTime(),
                rate.getModifiedBy(), rate.getModifiedDateTime());
    }

    private static RateSummaryResponse convertRateSummary(Rate rate) {
        if (rate == null) {
            return null;
        }
        return new RateSummaryResponse(rate.getId(), rate.getYear(), rate.getRate());
    }

//    private static Intermediary convertIntermediary(IntermediaryRequest intermediaryRequest) {
//        if (intermediaryRequest == null) {
//            return null;
//        }
//        Intermediary intermediary = new Intermediary();
//        intermediary.setId(intermediaryRequest.getId());
//
//        return intermediary;
//    }

//    private static IntermediaryResponse convertIntermediaryResponse(Intermediary intermediary) {
//        if (intermediary == null) {
//            return null;
//        }
//        return new IntermediaryResponse(intermediary.getId(), intermediary.getName(), intermediary.getDescription(),
//                intermediary.getEmail(), intermediary.getStatus(), intermediary.getCreatedBy(), intermediary.getCreatedDateTime(),
//                intermediary.getModifiedBy(), intermediary.getModifiedDateTime());
//    }


    private static ProductResponse convertProductResponse(InsuranceProduct product) {
        if (product == null) {
            return null;
        }
        return new ProductResponse(product.getId(), product.getProCode(), product.getProName(), product.getProDescription(),
                product.getStatus(), product.getCreatedBy(), product.getCreatedDateTime(),
                product.getModifiedBy(), product.getModifiedDateTime());
    }

//    private static RateTypeResponse convertRateTypeResponse(RateType rateType) {
//        if (rateType == null) {
//            return null;
//        }
//        return new RateTypeResponse(rateType.getId(), rateType.getExType(), rateType.getExDesc(), rateType.getStatus(),
//                rateType.getInactiveReason(), convertIntermediaryResponse(rateType.getIntermediary()), rateType.getCreatedBy(),
//                rateType.getCreatedDateTime(), rateType.getModifiedBy(), rateType.getModifiedDateTime());
//    }
//
//    private static RateType convertRateType(RateTypeRequest rateTypeRequest) {
//        if (rateTypeRequest == null) {
//            return null;
//        }
//        RateType rateType = new RateType();
//        rateType.setId(rateTypeRequest.getId());
//
//        return rateType;
//    }
}
