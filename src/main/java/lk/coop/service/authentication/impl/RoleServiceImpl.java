package lk.coop.service.authentication.impl;


import lk.coop.dto.authentication.request.RoleSaveRequest;
import lk.coop.dto.authentication.response.RoleResponse;
import lk.coop.entity.authentication.Role;
import lk.coop.repository.authentication.RoleRepository;
import lk.coop.service.authentication.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        return role;
    }

    @Override
    public RoleResponse findById(String id) {
        return convertRole(roleRepository.findById(id).get());
    }


    @Override
    public List<RoleResponse> notDeletedList() {

        return roleRepository.findAll()
                .stream().map(RoleServiceImpl::convertRole)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse save(RoleSaveRequest roleSaveRequest) {
        try {
            return convertRole(roleRepository.save(convertReq(roleSaveRequest, false)));
        } catch (Exception e) {
            log.error("Error Save Role {} ", roleSaveRequest.getName());
            return null;
        }
    }

    @Override
    public RoleResponse update(RoleSaveRequest roleSaveRequest) {
        try {
            Role role = this.roleRepository.getOne(roleSaveRequest.getId());
            if (role != null) {
                Role update = this.roleRepository.save(convert(roleSaveRequest, role));
                return convertRole(update);
            } else {
                log.info("Role Not Found! {} ", roleSaveRequest.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error Update Role {} ", roleSaveRequest.getId());
            return null;
        }
    }


    private static Role convertReq(RoleSaveRequest roleSaveRequest, boolean update) {
        Role role = new Role();
        role.setDescription(roleSaveRequest.getDescription());
        role.setName(roleSaveRequest.getName());
        if (update) {
            role.setId(roleSaveRequest.getId());
        }
        return role;
    }

    /**
     * UPDATE
     */
    private Role convert(RoleSaveRequest roleUpdateRequest, Role role) {
        role.setId(roleUpdateRequest.getId());
        role.setDescription(roleUpdateRequest.getDescription());
        role.setName(roleUpdateRequest.getName());

        return role;
    }

    static RoleResponse convertRole(Role role) {
        if (role == null) {
            return null;
        }
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        return roleResponse;
    }
}
