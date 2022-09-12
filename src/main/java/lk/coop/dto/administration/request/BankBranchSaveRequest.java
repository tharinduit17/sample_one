package lk.coop.dto.administration.request;


import lombok.Data;

@Data
public class BankBranchSaveRequest {
    private String code;
    private String name;
    private String grade;
    private String province;
    private String email;
    private String area;
    private String adminDistrict;
    private IntermediaryRequest intermediary;
}
