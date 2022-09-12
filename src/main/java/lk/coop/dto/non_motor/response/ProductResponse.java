package lk.coop.dto.non_motor.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;


@Value
public class ProductResponse {
    private String id;
    private String proCode;
    private String proName;
    private String proDescription;
//    private String insuranceClassId;
//    public IntermediaryRequest intermediary;
    private Status status;
    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;
}
