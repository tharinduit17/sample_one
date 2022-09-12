package lk.coop.dto.non_motor.response;

import lk.coop.enums.Status;
import lombok.Value;


@Value
public class IntermediaryResponse {
    private String id;
    private String name;
    private String description;
    private String email;
    private Status status;
}
