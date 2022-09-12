package lk.coop.dto.administration.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;


@Data
@Valid
public class IntermediarySaveRequest {
    private String name;
    private String description;
    private String email;
}
