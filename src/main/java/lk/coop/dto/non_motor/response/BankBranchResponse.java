package lk.coop.dto.non_motor.response;

import lk.coop.enums.Status;
import lombok.Value;

@Value
public class BankBranchResponse {
    private String id;
    private String code;
    private String name;
    private String province;
    private String email;
    private String area;
    private String adminDistrict;
    private Status status;
}
