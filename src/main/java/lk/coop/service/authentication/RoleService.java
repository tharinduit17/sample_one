package lk.coop.service.authentication;


import lk.coop.dto.authentication.request.RoleSaveRequest;
import lk.coop.dto.authentication.response.RoleResponse;
import lk.coop.entity.authentication.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    Role findByName(String name);

    RoleResponse findById(String id);

//    List<RoleResponse> activeList();

    List<RoleResponse> notDeletedList();

    RoleResponse save(RoleSaveRequest roleSaveRequest);

    RoleResponse update(RoleSaveRequest roleSaveRequest);

}
