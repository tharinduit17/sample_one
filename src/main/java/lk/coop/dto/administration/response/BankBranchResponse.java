package lk.coop.dto.administration.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class BankBranchResponse {
    private String id;
    private String code;
    private String name;
    private String grade;
    private String province;
    private String email;
    private String area;
    private String adminDistrict;
    private Status status;
    private IntermediaryResponse intermediary;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
