package lk.coop.dto.non_motor.response;

import lk.coop.enums.Status;
import lombok.Value;

import java.util.Date;

@Value
public class ConstructionItemsResponse {
    private String id;
    private String itemName ;
    private String itemDesc;
    private Status status;
}
