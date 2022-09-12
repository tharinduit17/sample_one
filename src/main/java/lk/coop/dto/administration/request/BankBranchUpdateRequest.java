package lk.coop.dto.administration.request;


import lk.coop.enums.Status;
import lombok.Data;

@Data
public class BankBranchUpdateRequest {
    private String code;
    private String name;
    private String grade;
    private String province;
    private String email;
    private String area;
    private String adminDistrict;
    private IntermediaryRequest intermediary;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;
}
