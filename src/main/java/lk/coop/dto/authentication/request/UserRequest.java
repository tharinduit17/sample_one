package lk.coop.dto.authentication.request;

import lk.coop.dto.administration.request.BankBranchRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lombok.Data;

import javax.validation.Valid;
import java.util.Set;


@Data
@Valid
public class UserRequest {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String name;
    private Set<RoleSaveRequest> roles;
    private IntermediaryRequest intermediary;
    private BankBranchRequest bankBranch;
}
