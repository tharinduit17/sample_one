package lk.coop.service.administration;

import lk.coop.dto.administration.request.ExcessUpdateRequest;
import lk.coop.dto.administration.request.ProductCoverSaveRequest;
import lk.coop.dto.administration.request.ProductCoverUpdateRequest;
import lk.coop.dto.administration.response.BankBranchResponse;
import lk.coop.dto.administration.response.ExcessResponse;
import lk.coop.dto.administration.response.ProductCoverResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCoverService {

    List<ProductCoverResponse> save(List<ProductCoverSaveRequest> saveRequest);

    ProductCoverResponse update (ProductCoverUpdateRequest updateRequest);

    List<ProductCoverResponse> getAll();

    List<ProductCoverResponse> getAllActive();

    ProductCoverResponse getById(String id);

    ProductCoverResponse delete (String id);

    List<ProductCoverResponse> getByProductId(String productId);
}
