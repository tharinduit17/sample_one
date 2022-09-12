package lk.coop.dto.administration.request;


import lk.coop.enums.Status;
import lombok.Data;

@Data
public class InsuranceTypeUpdateRequest {

    private String id;
    private String inType;
    private String inDesc;
    private Status status;
    private String inactiveReason;
    private IntermediaryRequest intermediary;

}
