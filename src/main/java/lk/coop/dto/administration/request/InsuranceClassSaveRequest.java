package lk.coop.dto.administration.request;

import lombok.Data;


@Data
public class InsuranceClassSaveRequest {
    private String code;
    private String name;
    private String description;
}
