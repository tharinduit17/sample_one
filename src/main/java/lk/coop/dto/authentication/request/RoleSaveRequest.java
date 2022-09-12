package lk.coop.dto.authentication.request;

import lombok.Data; import lk.coop.enums.*;

import javax.validation.Valid;
import java.util.List;


@Data
@Valid
public class RoleSaveRequest {
    private  String id;
    private String name;
    private String description;
}
