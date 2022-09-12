package lk.coop.controller.authentication;

import lk.coop.dto.authentication.request.RoleSaveRequest;
import lk.coop.dto.authentication.response.RoleResponse;
import lk.coop.service.authentication.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("Create")
    public ResponseEntity<RoleResponse> save(@RequestBody RoleSaveRequest request){
        RoleResponse save=roleService.save(request);
        return ResponseEntity.ok(save);
    }

    @PutMapping
    public ResponseEntity<RoleResponse> update(@RequestBody RoleSaveRequest updateRequest){
        RoleResponse update=roleService.update(updateRequest);
        return ResponseEntity.ok(update);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<Integer> delete(@PathVariable("id") @NotBlank String id){
//        Integer deleted = roleService.delete(id);
//        if(deleted==null){
//            return  ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(deleted);
//    }

    @GetMapping("{id}")
    public ResponseEntity<RoleResponse> getOneById(@PathVariable String id){
        RoleResponse get=roleService.findById(id);

        if(get==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(get);
    }

//    @GetMapping("Active")
//    public ResponseEntity<List<RoleResponse>> getActiveList(){
//        List<RoleResponse> active = roleService.activeList();
//        return ResponseEntity.ok(active);
//    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getNotDeletedList(){
        List<RoleResponse> notDeleted = roleService.notDeletedList();
        return ResponseEntity.ok(notDeleted);
    }



//    @GetMapping("GetByRoleId/{roleId}")
//    public @ResponseBody ResponseEntity<List<AuMenuItemsResponse>> getListByRoleId(@PathVariable String roleId){
//        List<AuMenuItemsResponse> list=auMenuItemsService.findByRoleId(roleId);
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("GetListByRoleAndParent/{roleId}/{parentId}")
//    public @ResponseBody ResponseEntity<List<AuMenuItemsResponse>> getListByRoleAndParent(@PathVariable String roleId,@PathVariable String parentId){
//        List<AuMenuItemsResponse> list=auMenuItemsService.findByParentAndRoleIds(roleId,parentId);
//        return ResponseEntity.ok(list);
//    }

}
