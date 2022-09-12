package lk.coop.dto.non_motor.response;

import lk.coop.enums.Status;
import lombok.Value;

@Value
public class PDocumentResponse {
    private String id;
    private String dcType ;
//    private String dcDesc;
//    private Status status;
}
