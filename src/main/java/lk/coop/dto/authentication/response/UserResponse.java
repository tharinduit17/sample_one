package lk.coop.dto.authentication.response;

import lk.coop.dto.non_motor.response.BankBranchSumResponse;
import lombok.Value;

import java.util.List;

@Value
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String phone;
    private String name;
//    private String businessTitle;
//    private String epfNo;
    private List<RoleResponse> roles;
    private BankBranchSumResponse bankBranch;
}
