package lk.coop.dto.authentication.request;

import lombok.Data;

import javax.validation.Valid;


@Data
@Valid
public class RoleRequest {
    private  String id;
}
