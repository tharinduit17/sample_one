package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.InsuranceTypeSaveRequest;
import lk.coop.dto.administration.request.InsuranceTypeUpdateRequest;
import lk.coop.dto.administration.response.InsuranceTypeResponse;
import lk.coop.service.administration.InsuranceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "InsuranceType")
@RestController
@RequestMapping("bcInsuType")
public class InsuranceTypeController {

    @Autowired
    InsuranceTypeService insuranceTypeService;

    /**
     * Create the Save Mapping
     * */
    @PostMapping
    public ResponseEntity<InsuranceTypeResponse> save(@RequestBody InsuranceTypeSaveRequest insuranceSaveRequest){
        InsuranceTypeResponse save=insuranceTypeService.save(insuranceSaveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Edit Mapping
     * */
    @PutMapping
    public ResponseEntity<InsuranceTypeResponse>update(@RequestBody InsuranceTypeUpdateRequest insuranceTypeUpdateRequest){
        InsuranceTypeResponse update=insuranceTypeService.update(insuranceTypeUpdateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the GetById Mapping
     * */
    @GetMapping("{id}")
    public ResponseEntity<InsuranceTypeResponse> getOneById(@PathVariable String id){
        InsuranceTypeResponse get=insuranceTypeService.findById(id);
        if(get==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Mapping
     * */
    @GetMapping
    public ResponseEntity<List<InsuranceTypeResponse>> getAllActive(){
        List<InsuranceTypeResponse> notDeleted = insuranceTypeService.getAllActive();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create Delete Mapping
     * */
    @DeleteMapping("{id}")
    public ResponseEntity<InsuranceTypeResponse> deleteById(@PathVariable("id") @NotBlank String id){

        InsuranceTypeResponse insuranceTypeResponse =insuranceTypeService.delete(id);

        if(insuranceTypeResponse==null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(insuranceTypeResponse);
    }

}
