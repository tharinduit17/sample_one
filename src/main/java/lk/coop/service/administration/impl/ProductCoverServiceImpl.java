package lk.coop.service.administration.impl;

import lk.coop.dto.administration.request.*;
import lk.coop.dto.administration.response.*;
import lk.coop.entity.administration.*;
import lk.coop.entity.authentication.Role;
import lk.coop.enums.Deleted;
import lk.coop.enums.IsDefoult;
import lk.coop.enums.Status;
import lk.coop.repository.administration.ProductCoverRepository;
import lk.coop.service.administration.ProductCoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductCoverServiceImpl implements ProductCoverService {

    @Autowired
    private ProductCoverRepository productCoverRepository;


    @Override
    public List<ProductCoverResponse> save(List<ProductCoverSaveRequest> saveRequest) {
        try {
            return convertProductCover(this.productCoverRepository.saveAll(convertSaveRequest(saveRequest)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save Student Allocation {}", saveRequest);
            return null;
        }
    }

    @Override
    public ProductCoverResponse update(ProductCoverUpdateRequest updateRequest) {

        try {
            ProductCover productCover = this.productCoverRepository.getOne(updateRequest.getId());
            if (productCover != null) {
                ProductCover update = this.productCoverRepository.save(convertUpdate(updateRequest, productCover));
                return convertProductCoverResponse(update);
            } else {
                log.info("Product Cover Not Found! {} ", updateRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update Product Cover {} ", updateRequest.getId());
            return null;
        }
  }

    @Override
    public List<ProductCoverResponse> getAll() {
        return productCoverRepository.findByIsDeleted(Deleted.NO).stream().map(ProductCoverServiceImpl::convertProductCoverResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductCoverResponse> getAllActive() {
        return productCoverRepository.getActiveList(Status.ACTIVE, Deleted.NO).stream().map(ProductCoverServiceImpl::convertProductCoverResponse).collect(Collectors.toList());
    }

    @Override
    public ProductCoverResponse getById(String id) {

        return productCoverRepository.findById(id).map(productCover -> this.convertProductCoverResponse(productCover)).orElse(null);


//        try {
//            ProductCover productCover = productCovkk                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             erRepository.findById(id);
//            if (productCover != null) {
//                return convertProductCover(productCover);
//            } else {
//                log.info("NotFound ProductCover {}", id);
//                return null;
//            }
//        } catch (Exception e) {
//            log.error("Error findById ProductCover {}", id);
//            return null;
//        }
    }

    @Override
    public ProductCoverResponse delete(String id) {
        try {
            ProductCover response = productCoverRepository.getOne(id);
            if (response != null) {
                response.setIsDeleted(Deleted.YES);
                response.setStatus(Status.INACTIVE);
                ProductCover responseDeleted = productCoverRepository.save(response);
                return convertProductCoverResponse(responseDeleted);
            } else {
                log.info("ProductCover Not Found! {} ", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error Delete ProductCover{} ", id);
            return null;
        }
    }


    @Override
    public List<ProductCoverResponse> getByProductId(String insuranceProductId) {
        return productCoverRepository.findByInsuranceProductIdAndStatusAndIsDeleted(insuranceProductId, Status.ACTIVE, Deleted.NO).stream().map(ProductCoverServiceImpl::convertProductCoverResponse).collect(Collectors.toList());
    }


    /*** FUNCTION */

    /**
     * Save
     */
    private static List<ProductCover> convertSaveRequest(List<ProductCoverSaveRequest> productCoverSaveRequest) {

        List<ProductCover> productCover = new ArrayList<>();

        for(ProductCoverSaveRequest productCovers : productCoverSaveRequest) {

            ProductCover pc = new ProductCover();

            pc.setPc_code(productCovers.getPc_code());
            pc.setPc_description(productCovers.getPc_description());
            pc.setPc_rate(productCovers.getPc_rate());
            pc.setPc_excess(productCovers.getPc_excess());
            pc.setPc_excess_amount(productCovers.getPc_excess_amount());
            pc.setPc_tax_cover(productCovers.getPc_tax_cover());
            pc.setPc_cal_seq(productCovers.getPc_cal_seq());
            pc.setPc_event_limit(productCovers.getPc_event_limit());
            pc.setIsDefoult(productCovers.getIsDefoult());
            pc.setInsuranceProduct(convertInsuranceProduct(productCovers.getInsuranceProduct()));
            pc.setCover(convertCover(productCovers.getInsuranceCover()));
            pc.setStatus(Status.ACTIVE);
            pc.setIsDeleted(Deleted.NO);

            productCover.add(pc);

        }

        return productCover;

    }


    /**
     * Update
     */
    private static ProductCover convertUpdate(ProductCoverUpdateRequest productCoverUpdateRequest,ProductCover productCover) {

        productCover.setId(productCoverUpdateRequest.getId());
        productCover.setPc_code(productCoverUpdateRequest.getPc_code());
        productCover.setPc_description(productCoverUpdateRequest.getPc_description());
        productCover.setPc_rate(productCoverUpdateRequest.getPc_rate());
        productCover.setPc_excess(productCoverUpdateRequest.getPc_excess());
        productCover.setPc_excess_amount(productCoverUpdateRequest.getPc_excess_amount());
        productCover.setPc_tax_cover(productCoverUpdateRequest.getPc_tax_cover());
        productCover.setPc_cal_seq(productCoverUpdateRequest.getPc_cal_seq());
        productCover.setPc_event_limit(productCoverUpdateRequest.getPc_event_limit());
        productCover.setIsDefoult(productCoverUpdateRequest.getIsDefoult());
        productCover.setInsuranceProduct(convertInsuranceProduct(productCoverUpdateRequest.getInsuranceProduct()));
        productCover.setCover(convertCover(productCoverUpdateRequest.getInsuranceCover()));
//        productCover.setInactiveReason(((productCoverUpdateRequest.getStatus() == Status.ACTIVE) ? "" : productCoverUpdateRequest.getInactiveReason()));
        productCover.setStatus(productCoverUpdateRequest.getStatus());
        productCover.setInactiveReason(productCoverUpdateRequest.getInactiveReason());

        System.out.println(productCover.getInactiveReason());


        return productCover;

    }

    /**
     * View
     */
    private static List<ProductCoverResponse> convertProductCover(List<ProductCover> productCover) {

        if (productCover == null) {
            return null;
        }

        List<ProductCoverResponse> productCoverResponseList = new ArrayList<>();

        for (ProductCover i : productCover) {

            ProductCoverResponse productCoverResponse = new ProductCoverResponse();

            productCoverResponse.setId(i.getId());
            productCoverResponse.setPc_code(i.getPc_code());
            productCoverResponse.setPc_description(i.getPc_description());
            productCoverResponse.setPc_rate(i.getPc_rate());
            productCoverResponse.setPc_excess(i.getPc_excess());
            productCoverResponse.setPc_excess_amount(i.getPc_excess_amount());
            productCoverResponse.setPc_tax_cover(i.getPc_tax_cover());
            productCoverResponse.setPc_cal_seq(i.getPc_cal_seq());
            productCoverResponse.setPc_event_limit(i.getPc_event_limit());
            productCoverResponse.setIsDefoult(i.getIsDefoult());
            productCoverResponse.setInsuranceProduct(convertInsuranceProductResponse(i.getInsuranceProduct()));
            productCoverResponse.setInsuranceCover(convertCoverResponse(i.getCover()));
            productCoverResponse.setStatus(i.getStatus());
            productCoverResponse.setCreatedBy(i.getCreatedBy());
            productCoverResponse.setCreatedDateTime(i.getCreatedDateTime());
            productCoverResponse.setModifiedBy(i.getModifiedBy());
            productCoverResponse.setModifiedDateTime(i.getModifiedDateTime());
            productCoverResponse.setDeleted(i.getIsDeleted());
            productCoverResponse.setInactiveReason(i.getInactiveReason());


            productCoverResponseList.add(productCoverResponse);

        }

        return productCoverResponseList;

    }

    private static CoverResponse convertCoverResponse(Cover cover) {
        if (cover == null) {
            return null;
        }
        return new CoverResponse(
                cover.getId(),
                cover.getCo_code(),
                cover.getCo_description(),
                cover.getStatus(),
                cover.getInactiveReason(),
                cover.getIsDeleted(),
                cover.getCreatedBy(),
                cover.getCreatedDateTime(),
                cover.getModifiedBy(),
                cover.getModifiedDateTime());
    }

    private static Cover convertCover(CoverRequest coverRequest) {
        if (coverRequest == null) {
            return null;
        }
        Cover cover = new Cover();
        cover.setId(coverRequest.getId());

        return cover;
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
                insuranceProduct.getInactiveReason());
    }

    private static InsuranceProduct convertInsuranceProduct(InsuranceProductRequest insuranceProductRequest) {
        if (insuranceProductRequest == null) {
            return null;
        }
        InsuranceProduct insuranceProduct = new InsuranceProduct();
        insuranceProduct.setId(insuranceProductRequest.getId());

        return insuranceProduct;
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


    private static ProductCoverResponse convertProductCoverResponse(ProductCover productCover) {

            ProductCoverResponse productCoverResponse = new ProductCoverResponse();

            productCoverResponse.setId(productCover.getId());
            productCoverResponse.setPc_code(productCover.getPc_code());
            productCoverResponse.setPc_description(productCover.getPc_description());
            productCoverResponse.setPc_rate(productCover.getPc_rate());
            productCoverResponse.setPc_excess(productCover.getPc_excess());
            productCoverResponse.setPc_excess_amount(productCover.getPc_excess_amount());
            productCoverResponse.setPc_tax_cover(productCover.getPc_tax_cover());
            productCoverResponse.setPc_cal_seq(productCover.getPc_cal_seq());
            productCoverResponse.setPc_event_limit(productCover.getPc_event_limit());
            productCoverResponse.setIsDefoult(productCover.getIsDefoult());
            productCoverResponse.setInsuranceProduct(convertInsuranceProductResponse(productCover.getInsuranceProduct()));
            productCoverResponse.setInsuranceCover(convertCoverResponse(productCover.getCover()));
            productCoverResponse.setStatus(productCover.getStatus());
            productCoverResponse.setCreatedBy(productCover.getCreatedBy());
            productCoverResponse.setCreatedDateTime(productCover.getCreatedDateTime());
            productCoverResponse.setModifiedBy(productCover.getModifiedBy());
            productCoverResponse.setModifiedDateTime(productCover.getModifiedDateTime());
            productCoverResponse.setDeleted(productCover.getIsDeleted());
            productCoverResponse.setInactiveReason(productCoverResponse.getInactiveReason());



        return productCoverResponse;

    }
}
