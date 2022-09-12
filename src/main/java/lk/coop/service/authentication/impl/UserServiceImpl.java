
package lk.coop.service.authentication.impl;


import lk.coop.dto.administration.request.BankBranchRequest;
import lk.coop.dto.administration.request.IntermediaryRequest;
import lk.coop.dto.authentication.request.RoleSaveRequest;
import lk.coop.dto.authentication.request.UserRequest;
import lk.coop.dto.authentication.response.RoleResponse;
import lk.coop.dto.authentication.response.UserResponse;
import lk.coop.dto.non_motor.response.BankBranchSumResponse;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Intermediary;
import lk.coop.entity.authentication.Role;
import lk.coop.entity.authentication.User;
import lk.coop.repository.authentication.UserRepository;
import lk.coop.service.authentication.RoleService;
import lk.coop.service.authentication.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserResponse findOne(String username) {
        return convert(userRepository.findByUsername(username));
    }

    @Override
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        try {
            User user = userRepository.findByUsername(userRequest.getUserName());
            if (user == null) {
                User save = userRepository.save(convert(userRequest));
                return convert(save);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Save User {} ", userRequest.getName());
            return null;
        }
    }

    /**SAVE*/
    private User convert(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUserName());
        user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setName(userRequest.getName());
        user.setRoles(convertRole(userRequest.getRoles()));
        user.setIntermediary(convertIntermediary(userRequest.getIntermediary()));
        user.setBankBranch(convertBankBranch(userRequest.getBankBranch()));
        return user;
    }

    private Intermediary convertIntermediary(IntermediaryRequest intermediaryRequest) {
        Intermediary intermediary = new Intermediary();
        intermediary.setId(intermediaryRequest.getId());
        return intermediary;
    }

    private BankBranch convertBankBranch(BankBranchRequest bankBranchRequest) {
        BankBranch bankBranch = new BankBranch();
        bankBranch.setId(bankBranchRequest.getId());
        return bankBranch;
    }


    private static UserResponse convert(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getName(),
                convertRoleResponse(user.getRoles()),convertBranchResponse(user.getBankBranch()));
    }

    private static List<RoleResponse> convertRoleResponse(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        List<RoleResponse> roleResponseList = new ArrayList<>();
        for (Role role : roles) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            roleResponse.setDescription(role.getDescription());
            roleResponseList.add(roleResponse);
        }
        return roleResponseList;
    }

    private static List<Role> convertRole(Set<RoleSaveRequest> roles) {
        if (roles == null) {
            return null;
        }
        List<Role> roleList = new ArrayList<>();
        for (RoleSaveRequest roleSaveRequest : roles) {
            Role role = new Role();
            role.setId((roleSaveRequest.getId() == "") ? null : roleSaveRequest.getId());
            role.setName(roleSaveRequest.getName());
            role.setDescription(roleSaveRequest.getDescription());
            roleList.add(role);
        }

        return roleList;
    }



    /**BANK BRANCH RESPONSE RESPONSE CONVERT*/
    private static BankBranchSumResponse convertBranchResponse(BankBranch bankBranch) {
        if (bankBranch == null) {
            return null;
        }

        return new BankBranchSumResponse(bankBranch.getId(), bankBranch.getCode(),bankBranch.getName());
    }
}

