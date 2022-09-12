package lk.coop.service.non_motor.impl;

import lk.coop.dto.non_motor.request.ConstructionDetailItemRequest;
import lk.coop.dto.non_motor.request.DocumentTypeRequest;
import lk.coop.dto.non_motor.request.NonMTProposalSaveRequest;
import lk.coop.dto.non_motor.request.ProductRequest;
import lk.coop.dto.non_motor.response.*;
import lk.coop.entity.administration.*;
import lk.coop.entity.non_motor.*;
import lk.coop.enums.Deleted;
import lk.coop.enums.IsPolicy;
import lk.coop.enums.Status;
import lk.coop.repository.non_motor.NonMTProposalRepository;
import lk.coop.repository.non_motor.NonMTQuotationRepository;
import lk.coop.service.non_motor.NonMTProposalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NonMTProposalServiceImpl implements NonMTProposalService {

    @Value("${intermediary.id}")
    private String INTER_ID;

    @Value("${intermediary.code}")
    private String INTER_CODE;

    @Autowired
    private NonMTQuotationRepository nonMTQuotationRepository;

    @Autowired
    private NonMTProposalRepository proposalRepository;

    @Override
    public PSummeryResponse save(NonMTProposalSaveRequest saveRequest) {
        try {
            NonMTQuotation bcNonMTQuotation = this.nonMTQuotationRepository.getOne(saveRequest.getQuotationId(), INTER_ID);
            return convertProposalSummery(this.proposalRepository.save(convertReq(saveRequest, bcNonMTQuotation)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Proposal {}", saveRequest.getInsuredName());
            return null;
        }
    }

//    @Override
//    public QSummeryResponse update(NonMTQuotationUpdateRequest updateRequest) {
//
//        try {
//            NonMTQuotation bcNonMTQuotation = this.nonMTQuotationRepository.getOne(updateRequest.getId(), INTER_ID);
//            if (bcNonMTQuotation != null) {
//                NonMTQuotation update = this.nonMTQuotationRepository.save(convertUpdate(updateRequest, bcNonMTQuotation));
//                NonMTQuotation oldInactive = this.nonMTQuotationRepository.save(inactiveOldQuotation(bcNonMTQuotation));
//                return convertQuotationSummery(update);
//            } else {
//                log.info("Quotation Not Found! {} ", updateRequest.getId());
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("Error Update Quotation {} ", updateRequest.getId());
//            return null;
//        }
//    }
//
//    @Override
//    public QSummeryResponse proposal(NonMTQuotationUpdateRequest updateRequest) {
//        return null;
//    }
//
//
    @Override
    public List<PSummeryResponse> getAllActive(String branchId) {
        try {
            if (branchId == "1") {
                return this.proposalRepository.findByStatus(Status.ACTIVE).stream().map(NonMTProposalServiceImpl::convertProposalSummery).collect(Collectors.toList());
            }else {
                return this.proposalRepository.getActiveList(Status.ACTIVE,branchId,INTER_ID).stream().map(NonMTProposalServiceImpl::convertProposalSummery).collect(Collectors.toList());
            }

        } catch (Exception e) {
            log.error("Error Get Proposal List {} ", branchId);
            return null;
        }
    }

//    @Override
//    public List<NonMTQuotationResponse> getAll() {
//        return bankBranchRepository.getAllList(Deleted.NO,INTER_ID).stream().map(NonMTQuotationServiceImpl::convertBcNonMTQuotation).collect(Collectors.toList());
//    }

    @Override
    public ProposalResponse findById(String id) {
        try {
            NonMTProposal proposal = this.proposalRepository.getOne(id, INTER_ID);
            if (proposal != null) {
                return convertProposal(proposal);
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
    private NonMTProposal convertReq(NonMTProposalSaveRequest proposalSaveRequest, NonMTQuotation quotation) {
        /**TO DATE CALCULATION*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(proposalSaveRequest.getFromDate());
        cal.add(Calendar.YEAR, quotation.getCoverPeriod());
        Date toDate = cal.getTime();

        String proposalNo = proposalSaveRequest.getQuotationNo();

        NonMTProposal proposal = new NonMTProposal();
        proposal.setQuotationNo(proposalNo);
        proposal.setProposalNo(proposalNo);
        proposal.setTitle(convertTitle(proposalSaveRequest.getTitleId()));
        proposal.setInsuredName(proposalSaveRequest.getInsuredName());
        proposal.setNic(proposalSaveRequest.getNic());
        proposal.setPostalAddress(proposalSaveRequest.getPostalAddress());
        proposal.setFromDate(proposalSaveRequest.getFromDate());
        proposal.setToDate(toDate);
        proposal.setLoanNo(proposalSaveRequest.getLoanNo());
        proposal.setLandName(proposalSaveRequest.getLandName());
        proposal.setPremisesAddress(proposalSaveRequest.getPremisesAddress());
        proposal.setConstructionDetailsItems(convertConstructionItems(proposalSaveRequest.getConstructionDetailsItems()));
        proposal.setDocuments(convertDocument(proposalSaveRequest.getDocuments(), quotation.getQuotationNo()));
        proposal.setIntermediary(convertIntermediary(INTER_ID));
        proposal.setIsPolicy(IsPolicy.NO);
        /** QUOTATION DETAILS */
        proposal.setClassId(quotation.getClassId());
        proposal.setProduct(quotation.getProduct());
        proposal.setIntermediaryCode(quotation.getIntermediaryCode());
        proposal.setIntermediaryBranchCode(quotation.getIntermediaryBranchCode());
        proposal.setIntermediaryProductCode(quotation.getIntermediaryProductCode());
        proposal.setIntermediaryYearCode(quotation.getIntermediaryYearCode());
        proposal.setSeqNo(quotation.getSeqNo());
        proposal.setNetInsured(quotation.getNetInsured());
        proposal.setSumInsured(quotation.getSumInsured());
        proposal.setConstructionType(quotation.getConstructionType());
        proposal.setCoverPeriod(quotation.getCoverPeriod());
        proposal.setTotalPremium(quotation.getTotalPremium());
        proposal.setRiskLocations(convertProposalLocation(quotation.getRiskLocations(), proposalNo,
                quotation.getClassId(), quotation.getProduct().getId()));
        proposal.setBankBranch(quotation.getBankBranch());
        proposal.setIsPolicy(IsPolicy.NO);
        proposal.setStatus(Status.ACTIVE);
        proposal.setIsDeleted(Deleted.NO);

        return proposal;
    }

    /**
     * UPDATE
     */
//    private NonMTQuotation convertUpdate(NonMTQuotationUpdateRequest updateRequest, NonMTQuotation oldQuotation) {
//
//        Double policyFee = 500.0;
//        String bankCode = updateRequest.getBankBranch().getCode();
//        String productCode = updateRequest.getProduct().getCode();
//        Integer yearCode = updateRequest.getIntermediaryYearCode();
//        String newQtNO = oldQuotation.getQuotationNo();
////        String newQtNO = generateQuotationNo(INTER_CODE, bankCode, productCode, yearCode);
//        //PREMIUM CALCULATION
//        Double sumInsu = this.calculation.calculatePremium(productCode, updateRequest.getNetInsured(),
//                updateRequest.getCoverPeriod(), updateRequest.getProduct().getId());
//        Double srtc = this.calculation.calculateSRCCTc(productCode, updateRequest.getNetInsured(), updateRequest.getSrcc(), updateRequest.getTc());
//        Double taxWithPolicyfee = this.calculation.calculateTax((sumInsu + srtc), policyFee);
//
////        System.out.println("SRCC -"+ srtc);
////        System.out.println("TAX -"+ taxWithPolicyfee);
//        //PREMIUM CALCULATION
//        /**OLD QUOTATION*/
//        NonMTQuotation nonMTQuotation = new NonMTQuotation();
//        nonMTQuotation.setQuotationNo(newQtNO);
//        nonMTQuotation.setIntermediaryCode(oldQuotation.getIntermediaryCode());
//        nonMTQuotation.setSeqNo(oldQuotation.getSeqNo());
//        nonMTQuotation.setClassId(oldQuotation.getClassId());
//        nonMTQuotation.setIsProposal(oldQuotation.getIsProposal());
//        nonMTQuotation.setIsPolicy(oldQuotation.getIsPolicy());
//        nonMTQuotation.setIsDeleted(oldQuotation.getIsDeleted());
//
//        nonMTQuotation.setIntermediaryBranchCode(bankCode);
//        nonMTQuotation.setIntermediaryProductCode(productCode);
//        nonMTQuotation.setIntermediaryYearCode(yearCode);
//        nonMTQuotation.setIntermediary(convertIntermediary(INTER_ID));
//
//        nonMTQuotation.setInsuredName(updateRequest.getInsuredName());
//        //NET
//        nonMTQuotation.setNetInsured(updateRequest.getNetInsured());
//        //WITHOUT TAX
//        nonMTQuotation.setSumInsured(sumInsu);
//        nonMTQuotation.setConstructionType(updateRequest.getConstructionType());
//        nonMTQuotation.setCoverPeriod(updateRequest.getCoverPeriod());
//        nonMTQuotation.setPremisesAddress(updateRequest.getPremisesAddress());
////        System.out.println("TotalPremium-"+(sumInsu+srtc+taxWithPolicyfee+policyFee));
//        nonMTQuotation.setTotalPremium(sumInsu + srtc + taxWithPolicyfee + policyFee);
//
//        nonMTQuotation.setVersion(updateRequest.getVersion() + 1);
//        nonMTQuotation.setProduct(convertInsuranceProduct(updateRequest.getProduct()));
//        nonMTQuotation.setRiskLocations(convertRiskLocation(updateRequest.getLocation(), newQtNO,
//                updateRequest.getClassId(), updateRequest.getProduct().getId()));
//        nonMTQuotation.setBankBranch(convertBankBranch(updateRequest.getBankBranch().getId()));
//        nonMTQuotation.setStatus(Status.ACTIVE);
//
//        return nonMTQuotation;
//    }
    private static NonMTQuotation inactiveOldQuotation(NonMTQuotation quotation) {
        quotation.setRiskLocations(convertRiskLocationInactive(quotation.getRiskLocations()));
        quotation.setStatus(Status.INACTIVE);
        return quotation;
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

    private static TitleType convertTitle(String titleId) {
        if (titleId == null) {
            return null;
        }
        TitleType titleType = new TitleType();
        titleType.setId(titleId);
        return titleType;
    }

    private static DocumentType convertDocumentType(String documentTypeId) {
        if (documentTypeId == null) {
            return null;
        }
        DocumentType documentType = new DocumentType();
        documentType.setId(documentTypeId);
        return documentType;
    }

    /*** CONVERT LOCATION */
    private static List<ProposalLocation> convertProposalLocation(List<RiskLocation> locationRequests, String quotationNo, String classId, String productId) {
        if (locationRequests == null) {
            return null;
        }
        List<ProposalLocation> riskLocations = new ArrayList<>();
        for (RiskLocation locationRequest : locationRequests) {
            ProposalLocation location = new ProposalLocation();
//            location.setId(locationRequest.getId());
            location.setProposalNo(quotationNo);
            location.setLocationCode(locationRequest.getLocationCode());
            location.setLocationDescription(locationRequest.getLocationDescription());
            location.setLocationSumInsu(locationRequest.getLocationSumInsu());
            location.setClassId(classId);
            location.setProductId(productId);
            location.setPremisesAddress(locationRequest.getPremisesAddress());
            location.setRisks(convertProposalRisk(locationRequest.getRisks(), quotationNo, classId, productId));
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
    private static List<ProposalRisk> convertProposalRisk(List<Risk> riskRequests, String quotationNo, String classId, String productId) {
        if (riskRequests == null) {
            return null;
        }
        List<ProposalRisk> risks = new ArrayList<>();
        for (Risk riskRequest : riskRequests) {
            ProposalRisk risk = new ProposalRisk();
//            risk.setId(riskRequest.getId());
            risk.setProposalNo(quotationNo);
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
            risk.setCovers(riskRequest.getCovers());
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
//    private static List<Cover> convertCover(List<CoverRequest> coverRequests) {
//        if (coverRequests == null) {
//            return null;
//        }
//        List<Cover> covers = new ArrayList<>();
//        for (CoverRequest coverRequest : coverRequests) {
//            Cover cover = new Cover();
//            cover.setId(coverRequest.getId());
//            covers.add(cover);
//        }
//        return covers;
//    }

    /*** CONVERT CONSTRUCTION ITEM */
    private static List<ConstructionDetailsItems> convertConstructionItems(List<ConstructionDetailItemRequest> itemRequests) {
        if (itemRequests == null) {
            return null;
        }
        List<ConstructionDetailsItems> itemsList = new ArrayList<>();
        for (ConstructionDetailItemRequest itemRequest : itemRequests) {
            ConstructionDetailsItems constructionDetailsItems = new ConstructionDetailsItems();
            constructionDetailsItems.setId(itemRequest.getId());
            itemsList.add(constructionDetailsItems);
        }
        return itemsList;
    }

    /*** CONVERT DOCUMENT */
    private static List<NonMTProposalDocument> convertDocument(List<DocumentTypeRequest> documentTypeRequests, String proposalNo) {
        if (documentTypeRequests == null) {
            return null;
        }
        List<NonMTProposalDocument> nonMTProposalDocuments = new ArrayList<>();
        for (DocumentTypeRequest documentRequest : documentTypeRequests) {
            NonMTProposalDocument nonMTProposalDocument = new NonMTProposalDocument();
            nonMTProposalDocument.setProposalNo(proposalNo);
            nonMTProposalDocument.setImagePath("imagepath/" + proposalNo);
            nonMTProposalDocument.setImageNo(documentRequest.getImageNo());
            nonMTProposalDocument.setDocumentType(convertDocumentType(documentRequest.getDocumentTypeId()));
            nonMTProposalDocument.setStatus(Status.ACTIVE);
            nonMTProposalDocument.setIsDeleted(Deleted.NO);

            nonMTProposalDocuments.add(nonMTProposalDocument);
        }
        return nonMTProposalDocuments;
    }


    /**
     * VIEW
     */
    private static PSummeryResponse convertProposalSummery(NonMTProposal proposal) {
        if (proposal == null) {
            return null;
        }
        return new PSummeryResponse(proposal.getId(), convertBankBranch(proposal.getBankBranch()), proposal.getQuotationNo(),
                proposal.getPolicyNo(), proposal.getInsuredName(), proposal.getNetInsured(), proposal.getTotalPremium(),
                proposal.getIssuedDate(), proposal.getIsPolicy(), proposal.getNic(), proposal.getPostalAddress(), proposal.getFromDate(),
                proposal.getToDate(), proposal.getLoanNo(), proposal.getLandName(), proposal.getPremisesAddress(),
                proposal.getCoverPeriod(), proposal.getStatus());
    }

    private static ProposalResponse convertProposal(NonMTProposal proposal) {
        if (proposal == null) {
            return null;
        }
        return new ProposalResponse(proposal.getId(), proposal.getQuotationNo(), proposal.getProposalNo(), proposal.getPolicyNo(),
                proposal.getIntermediaryCode(), proposal.getIntermediaryBranchCode(), proposal.getIntermediaryProductCode(),
                proposal.getIntermediaryYearCode(), proposal.getSeqNo(), proposal.getClassId(), proposal.getInsuredName(),
                proposal.getNic(), proposal.getPostalAddress(), proposal.getFromDate(), proposal.getToDate(), proposal.getLoanNo(),
                proposal.getLandName(), proposal.getNetInsured(), proposal.getSumInsured(), proposal.getConstructionType(),
                proposal.getCoverPeriod(), proposal.getPremisesAddress(), proposal.getTotalPremium(), proposal.getIsPolicy(),
                convertIntermediaryResponse(proposal.getIntermediary()), convertConstructionItemResponse(proposal.getConstructionDetailsItems()),
                convertBankBranch(proposal.getBankBranch()), convertProductSumResponse(proposal.getProduct()),
                convertPLocation(proposal.getRiskLocations()), convertPDocumentResponse(proposal.getDocuments()));
    }

    /**
     * Intermidiary Response
     */
    private static IntermediaryResponse convertIntermediaryResponse(Intermediary intermediary) {
        if (intermediary == null) {
            return null;
        }
        return new IntermediaryResponse(intermediary.getId(), intermediary.getName(), intermediary.getDescription(),
                intermediary.getEmail(), intermediary.getStatus());
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
    private static List<PLocationSumResponse> convertPLocation(List<ProposalLocation> locationSet) {
        if (locationSet == null) {
            return null;
        }
        List<PLocationSumResponse> locationSumResponses = new ArrayList<>();
        for (ProposalLocation location : locationSet) {
            locationSumResponses.add(new PLocationSumResponse(location.getId(), location.getLocationCode(), location.getLocationDescription(),
                    location.getLocationSumInsu(), location.getPremisesAddress(), convertPRisk(location.getRisks())));
        }
        return locationSumResponses;
    }

    /**
     * PRISK Response
     */
    private static List<PRiskResponse> convertPRisk(List<ProposalRisk> risks) {
        if (risks == null) {
            return null;
        }
        List<PRiskResponse> riskResponses = new ArrayList<>();
        for (ProposalRisk risk : risks) {
            riskResponses.add(new PRiskResponse(risk.getId(), risk.getRiskCode(), risk.getRiskDesc(),
                    risk.getRiskSum(), risk.getRiskRate(), convertCover(risk.getCovers())));
        }
        return riskResponses;
    }

    /**
     * CONSTRUCTION ITEMS RESPONSE
     */
    private static List<ConstructionItemsResponse> convertConstructionItemResponse(List<ConstructionDetailsItems> constructionDetailsItems) {
        if (constructionDetailsItems == null) {
            return null;
        }
        List<ConstructionItemsResponse> constructionItemsResponses = new ArrayList<>();
        for (ConstructionDetailsItems item : constructionDetailsItems) {
            constructionItemsResponses.add(new ConstructionItemsResponse(item.getId(),
                    item.getItemName(), item.getItemDesc(), item.getStatus()));
        }
        return constructionItemsResponses;
    }

    /**
     * PDOCUMENT RESPONSE RESPONSE
     */
    private static List<PDocumentResponse> convertPDocumentResponse(List<NonMTProposalDocument> documents) {
        if (documents == null) {
            return null;
        }
        List<PDocumentResponse> documentResponses = new ArrayList<>();
        for (NonMTProposalDocument doc : documents) {
            documentResponses.add(new PDocumentResponse(doc.getId(), doc.getDocumentType().getDcType()));
        }
        return documentResponses;
    }

    /**
     * COVER RESPONSE
     */
    private static Set<CoverResponse> convertCover(List<Cover> covers) {
        if (covers == null) {
            return null;
        }
        Set<CoverResponse> coverResponses = new HashSet<>();
        for (Cover cover : covers) {
            coverResponses.add(new CoverResponse(cover.getId(), cover.getCo_code(), cover.getCo_description()));
        }
        return coverResponses;
    }


}
