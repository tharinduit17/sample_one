//package lk.coop.controller.authentication;
//
//
//import lk.coop.dto.authentication.request.LoginSaveRequest;
//import lk.coop.dto.authentication.request.LoginUserNameRequest;
//import lk.coop.dto.authentication.response.LoginResponse;
//import lk.coop.service.authentication.LoginLoggerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/loginlog")
//public class LoginLoggerController {
//
//    @Autowired
//    private LoginLoggerService loggerService;
//
//
//    @PostMapping
//    public ResponseEntity<LoginResponse> save(@Valid @RequestBody LoginSaveRequest request) {
//        LoginResponse save = this.loggerService.save(request);
//        return ResponseEntity.ok(save);
//    }
//
//    @PostMapping("username")
//    private ResponseEntity<List<LoginResponse>> getByUserName(@Valid @RequestBody LoginUserNameRequest request) {
//        List<LoginResponse> responses = this.loggerService.getByUserName(request.getUserName());
//        if (responses.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(responses);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<LoginResponse> getOne(@PathVariable("id") String id) {
//        LoginResponse loginResponse = this.loggerService.getById(id);
//        if (loginResponse == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(loginResponse);
//    }
//}
