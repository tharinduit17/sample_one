package lk.coop.dto.administration.request;

import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class InsuranceTypeRequest {
    private  String id;
}
