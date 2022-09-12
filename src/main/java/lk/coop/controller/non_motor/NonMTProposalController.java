
package lk.coop.controller.non_motor;


import io.swagger.annotations.Api;
import lk.coop.dto.non_motor.request.NonMTProposalSaveRequest;
import lk.coop.dto.non_motor.response.PSummeryResponse;
import lk.coop.dto.non_motor.response.ProposalResponse;
import lk.coop.service.non_motor.NonMTProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "MTProposal")
@RestController
@RequestMapping("/proposal")
public class NonMTProposalController {

    @Autowired
    private NonMTProposalService nonMTProposalService;

    @PostMapping
    public ResponseEntity<PSummeryResponse> save(@RequestBody NonMTProposalSaveRequest request) {
        PSummeryResponse save = this.nonMTProposalService.save(request);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Update Mapping
     */
//    @PutMapping
//    public ResponseEntity<QSummeryResponse> update(@RequestBody NonMTQuotationUpdateRequest updateRequest) {
//        QSummeryResponse update = nonMTQuotationService.update(updateRequest);
//        return ResponseEntity.ok(update);
//    }
//
    /**
     * Create the Get by Id Mapping
     */
    @GetMapping("{id}")
    public ResponseEntity<ProposalResponse> getOneById(@PathVariable String id) {
        ProposalResponse proposalResponse = this.nonMTProposalService.findById(id);
        if (proposalResponse == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proposalResponse);
    }

    /**
     * Create the Get All Active
     */
    @GetMapping("/active/{bid}")
    public ResponseEntity<List<PSummeryResponse>> getAllActive(@PathVariable String bid) {
        List<PSummeryResponse> allActive = this.nonMTProposalService.getAllActive(bid);
        return ResponseEntity.ok(allActive);
    }

}

