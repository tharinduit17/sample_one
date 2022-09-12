package lk.coop.dto.authentication.administration.request;

import lombok.Data;

import javax.validation.Valid;


@Data
@Valid
public class RoleRequest {
    private  String id;
}
