
package lk.coop.controller.non_motor;


import io.swagger.annotations.Api;
import lk.coop.calculation.Calculation;
import lk.coop.dto.non_motor.request.NonMTQuotationSaveRequest;
import lk.coop.dto.non_motor.request.NonMTQuotationUpdateRequest;
import lk.coop.dto.non_motor.response.QSummeryResponse;
import lk.coop.dto.non_motor.response.QuotationResponse;
import lk.coop.service.non_motor.NonMTQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "NonMTQuotation")
@RestController
@RequestMapping("/quotation")
public class NonMTQuotationController {

    @Autowired
    private NonMTQuotationService nonMTQuotationService;

    @Autowired
    private Calculation calculation;

    @PostMapping
    public ResponseEntity<QSummeryResponse> save(@RequestBody NonMTQuotationSaveRequest request) {
        QSummeryResponse save = nonMTQuotationService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
    @PutMapping
    public ResponseEntity<QSummeryResponse> update(@RequestBody NonMTQuotationUpdateRequest updateRequest) {
        QSummeryResponse update = nonMTQuotationService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<QuotationResponse> getOneById(@PathVariable String id) {
        QuotationResponse quotationResponse = nonMTQuotationService.findById(id);
        if (quotationResponse == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quotationResponse);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active/{bid}")
    public ResponseEntity<List<QSummeryResponse>> getAllActive(@PathVariable String bid) {
        List<QSummeryResponse> allActive = nonMTQuotationService.getAllActive(bid);
        return ResponseEntity.ok(allActive);
    }

}

