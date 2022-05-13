package Controller;


import Services.UserServices;
import dormain.ResponseObject;
import dormain.Role;
import dormain.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path="/api/v1/")
@RequiredArgsConstructor

public class UserController {

    private UserServices userServices;
    public Boolean passwordValidator(String password){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    ResponseEntity<ResponseObject> response(HttpStatus status, String message,Object data){
        return ResponseEntity.status(status).body(new ResponseObject(status.toString(), message,data));
    }
    @GetMapping("/Users")
    public ResponseEntity<ResponseObject> getAllUser(){
        return response(HttpStatus.OK,"Get All Users Successfully",userServices.getAllUsers());
    }

    @PostMapping("/Registration")
    ResponseEntity<ResponseObject> registrationUser(@RequestBody User newUsers){
        User foundUsers =userServices.getUser(newUsers.getUsername().trim());

        if(foundUsers!=null){
            return response(HttpStatus.NOT_IMPLEMENTED,"Username already exists","");
        }
        if (!passwordValidator(newUsers.getPassword())){
            return response(HttpStatus.PRECONDITION_FAILED,"Password requirements do not meet","");
        }

        return response(HttpStatus.CREATED,"Registration Successfully",userServices.saveUser(newUsers));
    }

    @PostMapping("/AddRole")
    ResponseEntity<ResponseObject> addRoleToUser(@RequestBody addRoleForm form){
        User foundUsers=userServices.getUser(form.getUsername());
        if(foundUsers==null){
            return response(HttpStatus.NOT_IMPLEMENTED,"Username does not exists","");
        }
        userServices.addRole(form.getUsername(),form.getRoleName());
        return response(HttpStatus.CREATED,"Role Added Successfully","");
    }

    @Data
    class addRoleForm{
        private String username;
        private String roleName;
    }

}
