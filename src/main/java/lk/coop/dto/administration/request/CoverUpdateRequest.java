package lk.coop.dto.administration.request;


import lk.coop.enums.Status;
import lombok.Data;

@Data
public class CoverUpdateRequest {

    private Integer co_code;
    private String co_description;
    /**UPDATE*/
    private String id;
    private Status status;
    private String inactiveReason;

}
