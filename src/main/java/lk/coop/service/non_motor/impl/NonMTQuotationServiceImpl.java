package lk.coop.service.non_motor.impl;

import lk.coop.calculation.Calculation;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.dto.non_motor.request.*;
import lk.coop.dto.non_motor.response.*;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Cover;
import lk.coop.entity.administration.InsuranceProduct;
import lk.coop.entity.administration.Intermediary;
import lk.coop.entity.non_motor.NonMTQuotation;
import lk.coop.entity.non_motor.Risk;
import lk.coop.entity.non_motor.RiskLocation;
import lk.coop.enums.Deleted;
import lk.coop.enums.IsPolicy;
import lk.coop.enums.IsProposal;
import lk.coop.enums.Status;
import lk.coop.repository.non_motor.NonMTQuotationRepository;
import lk.coop.service.non_motor.NonMTQuotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NonMTQuotationServiceImpl implements NonMTQuotationService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Value("${intermediary.code}")
    private String INTER_CODE;

    @Autowired
    private NonMTQuotationRepository nonMTQuotationRepository;

    @Autowired
    private Calculation calculation;

    @Override
    public QSummeryResponse save(NonMTQuotationSaveRequest saveRequest) {
        try {
            return convertQuotationSummery(this.nonMTQuotationRepository.save(convertReq(saveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Quotation {}", saveRequest.getInsuredName());
            return null;
        }
    }


    @Override
    public QSummeryResponse update(NonMTQuotationUpdateRequest updateRequest) {

        try {
            NonMTQuotation bcNonMTQuotation = this.nonMTQuotationRepository.getOne(updateRequest.getId(), INTER_ID);
            if (bcNonMTQuotation != null) {
                NonMTQuotation update = this.nonMTQuotationRepository.save(convertUpdate(updateRequest, bcNonMTQuotation));
                NonMTQuotation oldInactive = this.nonMTQuotationRepository.save(inactiveOldQuotation(bcNonMTQuotation));
                return convertQuotationSummery(update);
            } else {
                log.info("Quotation Not Found! {} ", updateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update Quotation {} ", updateRequest.getId());
            return null;
        }
    }

    @Override
    public QSummeryResponse proposal(NonMTQuotationUpdateRequest updateRequest) {
        return null;
    }


    @Override
    public List<QSummeryResponse> getAllActive(String branchId) {
        try {
            if (branchId.equals("1")) {
                return this.nonMTQuotationRepository.findByStatus(Status.ACTIVE).stream().map(NonMTQuotationServiceImpl::convertQuotationSummery).collect(Collectors.toList());
            } else {
                return this.nonMTQuotationRepository.getActiveList(Status.ACTIVE, branchId, INTER_ID).stream().map(NonMTQuotationServiceImpl::convertQuotationSummery).collect(Collectors.toList());
            }

        } catch (Exception e) {
            log.error("Error Update Quotation {} ", branchId);
            return null;
        }
    }

//    @Override
//    public List<NonMTQuotationResponse> getAll() {
//        return bankBranchRepository.getAllList(Deleted.NO,INTER_ID).stream().map(NonMTQuotationServiceImpl::convertBcNonMTQuotation).collect(Collectors.toList());
//    }

    @Override
    public QuotationResponse findById(String id) {
        try {
            NonMTQuotation quotation = this.nonMTQuotationRepository.getOne(id, INTER_ID);
            if (quotation != null) {
                return convertQuotation(quotation);
            } else {
                log.info("NotFound NonMTQuotation {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error findById NonMTQuotation {}", id);
            return null;
        }
    }

//    @Override
//    public NonMTQuotationResponse delete(String id) {
//        try {
//            NonMTQuotation response = bankBranchRepository.getOne(id, INTER_ID);
//            if (response != null) {
//                response.setIsDeleted(Deleted.YES);
//                response.setStatus(Status.INACTIVE);
//                NonMTQuotation responseDeleted = bankBranchRepository.save(response);
//                return convertBcNonMTQuotation(responseDeleted);
//            } else {
//                log.info("BOC Bank Branch Not Found! {} ", id);
//                return null;
//            }
//        } catch (Exception e) {
//            log.error("Error Delete BOC Bank Branch {} ", id);
//            return null;
//        }
//    }

    /*** FUNCTION */

    /**
     * SAVE
     */
    private NonMTQuotation convertReq(NonMTQuotationSaveRequest nonMTQuotationSaveRequest) {
        Double policyFee = 500.0;
        String bankCode = nonMTQuotationSaveRequest.getBankBranch().getCode();
        String productCode = nonMTQuotationSaveRequest.getProduct().getCode();
        Integer yearCode = nonMTQuotationSaveRequest.getIntermediaryYearCode();
        String newQtNO = generateQuotationNo(INTER_CODE, bankCode, productCode, yearCode);
        //PREMIUM CALCULATION
        Double sumInsu = this.calculation.calculatePremium(productCode, nonMTQuotationSaveRequest.getNetInsured(),
                nonMTQuotationSaveRequest.getCoverPeriod(), nonMTQuotationSaveRequest.getProduct().getId());
        Double srtc = this.calculation.calculateSRCCTc(productCode, nonMTQuotationSaveRequest.getNetInsured(), nonMTQuotationSaveRequest.getSrcc(), nonMTQuotationSaveRequest.getTc());
        Double taxWithPolicyfee = this.calculation.calculateTax((sumInsu + srtc), policyFee);

//        System.out.println("SRCC -"+ srtc);
//        System.out.println("TAX -"+ taxWithPolicyfee);
        //PREMIUM CALCULATION

        NonMTQuotation nonMTQuotation = new NonMTQuotation();
        nonMTQuotation.setQuotationNo(newQtNO);
        nonMTQuotation.setIntermediaryCode(INTER_CODE);
        nonMTQuotation.setIntermediaryBranchCode(bankCode);
        nonMTQuotation.setIntermediaryProductCode(productCode);
        nonMTQuotation.setIntermediaryYearCode(yearCode);
        nonMTQuotation.setIntermediary(convertIntermediary(INTER_ID));
        nonMTQuotation.setSeqNo(Integer.parseInt(newQtNO.substring(newQtNO.length() - 4)));
        nonMTQuotation.setClassId(nonMTQuotationSaveRequest.getClassId());
        nonMTQuotation.setInsuredName(nonMTQuotationSaveRequest.getInsuredName());
        //NET
        nonMTQuotation.setNetInsured(nonMTQuotationSaveRequest.getNetInsured());
        //WITHOUT TAX
        nonMTQuotation.setSumInsured(sumInsu);
        nonMTQuotation.setConstructionType(nonMTQuotationSaveRequest.getConstructionType());
        nonMTQuotation.setCoverPeriod(nonMTQuotationSaveRequest.getCoverPeriod());
        nonMTQuotation.setPremisesAddress(nonMTQuotationSaveRequest.getPremisesAddress());
//        System.out.println("TotalPremium-"+(sumInsu+srtc+taxWithPolicyfee+policyFee));
        nonMTQuotation.setTotalPremium(sumInsu + srtc + taxWithPolicyfee + policyFee);

        nonMTQuotation.setVersion(0);
        nonMTQuotation.setProduct(convertInsuranceProduct(nonMTQuotationSaveRequest.getProduct()));
        nonMTQuotation.setRiskLocations(convertRiskLocation(nonMTQuotationSaveRequest.getLocation(), newQtNO,
                nonMTQuotationSaveRequest.getClassId(), nonMTQuotationSaveRequest.getProduct().getId()));
        nonMTQuotation.setBankBranch(convertBankBranch(nonMTQuotationSaveRequest.getBankBranch().getId()));
        nonMTQuotation.setIsProposal(IsProposal.NO);
        nonMTQuotation.setIsPolicy(IsPolicy.NO);
        nonMTQuotation.setStatus(Status.ACTIVE);
        nonMTQuotation.setIsDeleted(Deleted.NO);

        return nonMTQuotation;
    }

    /**
     * UPDATE
     */
    private NonMTQuotation convertUpdate(NonMTQuotationUpdateRequest updateRequest, NonMTQuotation oldQuotation) {

        Double policyFee = 500.0;
        String bankCode = updateRequest.getBankBranch().getCode();
        String productCode = updateRequest.getProduct().getCode();
        Integer yearCode = updateRequest.getIntermediaryYearCode();
        String newQtNO = oldQuotation.getQuotationNo();
//        String newQtNO = generateQuotationNo(INTER_CODE, bankCode, productCode, yearCode);
        //PREMIUM CALCULATION
        Double sumInsu = this.calculation.calculatePremium(productCode, updateRequest.getNetInsured(),
                updateRequest.getCoverPeriod(), updateRequest.getProduct().getId());
        Double srtc = this.calculation.calculateSRCCTc(productCode, updateRequest.getNetInsured(), updateRequest.getSrcc(), updateRequest.getTc());
        Double taxWithPolicyfee = this.calculation.calculateTax((sumInsu + srtc), policyFee);

//        System.out.println("SRCC -"+ srtc);
//        System.out.println("TAX -"+ taxWithPolicyfee);
        //PREMIUM CALCULATION
        /**OLD QUOTATION*/
        NonMTQuotation nonMTQuotation = new NonMTQuotation();
        nonMTQuotation.setQuotationNo(newQtNO);
        nonMTQuotation.setIntermediaryCode(oldQuotation.getIntermediaryCode());
        nonMTQuotation.setSeqNo(oldQuotation.getSeqNo());
        nonMTQuotation.setClassId(oldQuotation.getClassId());
        nonMTQuotation.setIsProposal(oldQuotation.getIsProposal());
        nonMTQuotation.setIsPolicy(oldQuotation.getIsPolicy());
        nonMTQuotation.setIsDeleted(oldQuotation.getIsDeleted());

        nonMTQuotation.setIntermediaryBranchCode(bankCode);
        nonMTQuotation.setIntermediaryProductCode(productCode);
        nonMTQuotation.setIntermediaryYearCode(yearCode);
        nonMTQuotation.setIntermediary(convertIntermediary(INTER_ID));

        nonMTQuotation.setInsuredName(updateRequest.getInsuredName());
        //NET
        nonMTQuotation.setNetInsured(updateRequest.getNetInsured());
        //WITHOUT TAX
        nonMTQuotation.setSumInsured(sumInsu);
        nonMTQuotation.setConstructionType(updateRequest.getConstructionType());
        nonMTQuotation.setCoverPeriod(updateRequest.getCoverPeriod());
        nonMTQuotation.setPremisesAddress(updateRequest.getPremisesAddress());
//        System.out.println("TotalPremium-"+(sumInsu+srtc+taxWithPolicyfee+policyFee));
        nonMTQuotation.setTotalPremium(sumInsu + srtc + taxWithPolicyfee + policyFee);

        nonMTQuotation.setVersion(updateRequest.getVersion() + 1);
        nonMTQuotation.setProduct(convertInsuranceProduct(updateRequest.getProduct()));
        nonMTQuotation.setRiskLocations(convertRiskLocation(updateRequest.getLocation(), newQtNO,
                updateRequest.getClassId(), updateRequest.getProduct().getId()));
        nonMTQuotation.setBankBranch(convertBankBranch(updateRequest.getBankBranch().getId()));
        nonMTQuotation.setStatus(Status.ACTIVE);

        return nonMTQuotation;
    }

    private NonMTQuotation inactiveOldQuotation(NonMTQuotation quotation) {
        quotation.setRiskLocations(convertRiskLocationInactive(quotation.getRiskLocations()));
        quotation.setStatus(Status.INACTIVE);
        return quotation;
    }


    /*** GENERATE QUOTATION NO */
    private String generateQuotationNo(String intermediaryCode, String branchCode, String productCode, Integer year) {
        if (intermediaryCode == null && branchCode == null && productCode == null && year == null) {
            return null;
        }
        Integer maxSeqNo = this.nonMTQuotationRepository.getMaxSeqNo(intermediaryCode, branchCode, productCode, year);
        Integer newSeqNo = (maxSeqNo != null) ? maxSeqNo + 1 : 1;
        String newQtaNo = "Q" + intermediaryCode + branchCode + productCode + Integer.toString(year) + String.format("%04d", newSeqNo);
        return newQtaNo;
    }

    private static Intermediary convertIntermediary(String intermediaryRequest) {
        if (intermediaryRequest == null) {
            return null;
        }
        Intermediary intermediary = new Intermediary();
        intermediary.setId(intermediaryRequest);
        return intermediary;
    }

    private static InsuranceProduct convertInsuranceProduct(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }
        InsuranceProduct insuranceProduct = new InsuranceProduct();
        insuranceProduct.setId(productRequest.getId());
        return insuranceProduct;
    }

    private static BankBranch convertBankBranch(String bankBranchId) {
        if (bankBranchId == null) {
            return null;
        }
        BankBranch bankBranch = new BankBranch();
        bankBranch.setId(bankBranchId);
        return bankBranch;
    }

    /*** CONVERT LOCATION */
    private static List<RiskLocation> convertRiskLocation(Set<LocationRequest> locationRequests, String quotationNo, String classId, String productId) {
        if (locationRequests == null) {
            return null;
        }
        List<RiskLocation> riskLocations = new ArrayList<>();
        for (LocationRequest locationRequest : locationRequests) {
            RiskLocation location = new RiskLocation();
//          location.setId(locationRequest.getId());
            location.setQuotationNo(quotationNo);
            location.setLocationCode(locationRequest.getLocationCode());
            location.setLocationDescription(locationRequest.getLocationDescription());
            location.setLocationSumInsu(locationRequest.getLocationSumInsu());
            location.setClassId(classId);
            location.setProductId(productId);
            location.setPremisesAddress(locationRequest.getPremisesAddress());
            location.setRisks(convertRisk(locationRequest.getRisks(), quotationNo, classId, productId));
            location.setStatus(Status.ACTIVE);
            location.setIsDeleted(Deleted.NO);

            riskLocations.add(location);
        }

        return riskLocations;
    }

    private static List<RiskLocation> convertRiskLocationInactive(List<RiskLocation> locationRequests) {
        if (locationRequests == null) {
            return null;
        }
        List<RiskLocation> riskLocations = new ArrayList<>();
        for (RiskLocation locationRequest : locationRequests) {
            locationRequest.setRisks(convertRiskInactive(locationRequest.getRisks()));
            locationRequest.setStatus(Status.INACTIVE);
            riskLocations.add(locationRequest);
        }

        return riskLocations;
    }

    /*** CONVERT RISK */
    private static List<Risk> convertRisk(List<RiskRequest> riskRequests, String quotationNo, String classId, String productId) {
        if (riskRequests == null) {
            return null;
        }
        List<Risk> risks = new ArrayList<>();
        for (RiskRequest riskRequest : riskRequests) {
            Risk risk = new Risk();
//          risk.setId(riskRequest.getId());
            risk.setQuotationNo(quotationNo);
            risk.setClassId(classId);
            risk.setProductId(productId);
            risk.setRiskCode(riskRequest.getRiskCode());
            risk.setRiskDesc(riskRequest.getRiskDesc());
            risk.setRiskSum(riskRequest.getRiskSum());
            risk.setRiskRate(riskRequest.getRiskRate());
//            risk.setBasicPremium(riskRequest.getBasicPremium());
//            risk.setExcessAmount(riskRequest.getExcessAmount());
//            risk.setMinLimit(riskRequest.getMinLimit());
//            risk.setExcess(riskRequest.getExcess());
//            risk.setEventLimit(riskRequest.getEventLimit());
            risk.setCovers(convertCover(riskRequest.getCovers()));
            risk.setStatus(Status.ACTIVE);
            risk.setIsDeleted(Deleted.NO);

            risks.add(risk);
        }
        return risks;
    }

    //FOR UPDATE QUOTATION
    private static List<Risk> convertRiskInactive(List<Risk> riskRequests) {
        if (riskRequests == null) {
            return null;
        }
        List<Risk> risks = new ArrayList<>();
        for (Risk riskRequest : riskRequests) {
            riskRequest.setStatus(Status.INACTIVE);
            risks.add(riskRequest);
        }
        return risks;
    }

    /*** CONVERT COVER */
    private static List<Cover> convertCover(Set<CoverRequest> coverRequests) {
        if (coverRequests == null) {
            return null;
        }
        List<Cover> covers = new ArrayList<>();
        for (CoverRequest coverRequest : coverRequests) {
            Cover cover = new Cover();
            cover.setId(coverRequest.getId());
            covers.add(cover);
        }
        return covers;
    }

    /**
     * VIEW
     */
    private static QSummeryResponse convertQuotationSummery(NonMTQuotation quotation) {
        if (quotation == null) {
            return null;
        }
        return new QSummeryResponse(quotation.getId(), convertBranchResponse(quotation.getBankBranch()), quotation.getQuotationNo(),
                quotation.getInsuredName(), quotation.getNetInsured(),quotation.getModifiedDateTime() ,quotation.getTotalPremium(), quotation.getIsProposal(),
                quotation.getIsPolicy(), quotation.getCoverPeriod(), quotation.getVersion(), quotation.getStatus());
    }

    private static QuotationResponse convertQuotation(NonMTQuotation quotation) {
        if (quotation == null) {
            return null;
        }
        return new QuotationResponse(quotation.getId(), quotation.getClassId(),quotation.getQuotationNo(), quotation.getIntermediaryYearCode(),
                quotation.getInsuredName(), quotation.getNetInsured(), quotation.getConstructionType(), quotation.getCoverPeriod(), quotation.getPremisesAddress(),
                quotation.getVersion(), convertBankBranch(quotation.getBankBranch()), convertProductSumResponse(quotation.getProduct()),
                convertLocation(quotation.getRiskLocations()), quotation.getTotalPremium());
    }

    /**
     * BANK BRANCH
     */
    private static BankBranchSumResponse convertBankBranch(BankBranch bankBranch) {
        if (bankBranch == null) {
            return null;
        }
        return new BankBranchSumResponse(bankBranch.getId(), bankBranch.getCode(), bankBranch.getName());
    }

    /**
     * Product Response
     */
    private static ProductSumResponse convertProductSumResponse(InsuranceProduct product) {
        if (product == null) {
            return null;
        }
        return new ProductSumResponse(product.getId(), product.getProCode(), product.getProName());
    }

    /**
     * Product Response
     */
    private static Set<LocationSumResponse> convertLocation(List<RiskLocation> locationSet) {
        if (locationSet == null) {
            return null;
        }
        Set<LocationSumResponse> locationSumResponses = new HashSet<>();
        for (RiskLocation location : locationSet) {
            locationSumResponses.add(new LocationSumResponse(location.getId(), location.getLocationCode(), location.getLocationDescription(),
                    location.getLocationSumInsu(), location.getPremisesAddress(), convertRiskResponse(location.getRisks())));
        }
        return locationSumResponses;
    }

    /**
     * Product Response
     */
    private static List<RiskResponse> convertRiskResponse(List<Risk> risks) {
        if (risks == null) {
            return null;
        }
        List<RiskResponse> riskResponses = new ArrayList<>();
        for (Risk risk : risks) {
            riskResponses.add(new RiskResponse(risk.getId(), risk.getRiskCode(), risk.getRiskDesc(),
                    risk.getRiskSum(), risk.getRiskRate(), convertCoverResponse(risk.getCovers())));
        }
        return riskResponses;
    }

    /**
     * Product Response
     */
    private static List<CoverResponse> convertCoverResponse(List<Cover> covers) {
        if (covers == null) {
            return null;
        }
        List<CoverResponse> coverResponses = new ArrayList<>();
        for (Cover cover : covers) {
            coverResponses.add(new CoverResponse(cover.getId(), cover.getCo_code(), cover.getCo_description()));
        }
        return coverResponses;
    }

    /**
     * BANK BRANCH RESPONSE RESPONSE CONVERT
     */
    private static BankBranchSumResponse convertBranchResponse(BankBranch bankBranch) {
        if (bankBranch == null) {
            return null;
        }
        return new BankBranchSumResponse(bankBranch.getId(), bankBranch.getCode(), bankBranch.getName());
    }


}
