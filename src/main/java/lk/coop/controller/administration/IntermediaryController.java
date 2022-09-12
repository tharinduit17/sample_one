package lk.coop.controller.administration;


import io.swagger.annotations.Api;
import lk.coop.dto.administration.request.IntermediarySaveRequest;
import lk.coop.dto.administration.request.IntermediaryUpdateRequest;
import lk.coop.dto.administration.response.IntermediaryResponse;
import lk.coop.service.administration.IntermediaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Intermediary")
@RestController
@RequestMapping("intermediary")
public class IntermediaryController {

    @Autowired
    private IntermediaryService intermediaryService;

    /**
     * Create the Save Mapping
     * */
    @PostMapping
    public ResponseEntity<IntermediaryResponse> save(@RequestBody IntermediarySaveRequest intermediarySaveRequest){
        IntermediaryResponse save=intermediaryService.save(intermediarySaveRequest);
        return ResponseEntity.ok(save);
    }

    /**
     * Create the Edit Mapping
     * */
    @PutMapping
    public ResponseEntity<IntermediaryResponse>update(@RequestBody IntermediaryUpdateRequest intermediaryTypeUpdateRequest){
        IntermediaryResponse update=intermediaryService.update(intermediaryTypeUpdateRequest);
        return ResponseEntity.ok(update);
    }

    /**
     * Create the GetById Mapping
     * */
    @GetMapping("{id}")
    public ResponseEntity<IntermediaryResponse> getOneById(@PathVariable String id){
        IntermediaryResponse get=intermediaryService.findById(id);
        if(get==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(get);
    }

    /**
     * Create the Get All Mapping
     * */
    @GetMapping
    public ResponseEntity<List<IntermediaryResponse>> getAllActive(){
        List<IntermediaryResponse> notDeleted = intermediaryService.getAllActive();
        return ResponseEntity.ok(notDeleted);
    }

    /**
     * Create Delete Mapping
     * */
    @DeleteMapping("{id}")
    public ResponseEntity<IntermediaryResponse> deleteById(@PathVariable("id") @NotBlank String id){

        IntermediaryResponse intermediaryResponse =intermediaryService.delete(id);

        if(intermediaryResponse==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(intermediaryResponse);
    }
}
