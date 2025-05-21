package EMS.User_Service.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EMS.User_Service.DTO.AddEmployeeDTO;
import EMS.User_Service.DTO.AllEmployeeDTO;
import EMS.User_Service.DTO.EditEmployeeDTO;
import EMS.User_Service.DTO.Employee;
import EMS.User_Service.DTO.LoginResult;
import EMS.User_Service.Entity.LoginCredential;
import EMS.User_Service.Entity.User;
import EMS.User_Service.Exception.EmployeeNotFoundException;
import EMS.User_Service.Exception.InvalidEmployeeIdException;
import EMS.User_Service.Exception.InvalidUserIdException;
import EMS.User_Service.Service.UserService;
import jakarta.validation.Valid;

@RestController
public class Controller {
	
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("auth/login")
	 public ResponseEntity<LoginResult> loginHandler(@RequestBody LoginCredential body) throws InvalidUserIdException{
        return userService.verify(body);
	}
      
	
	@PostMapping("/employees")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody AddEmployeeDTO e) throws Exception{
		return userService.addEmployee(e);
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<AllEmployeeDTO>> getAllEmployees() throws AccessDeniedException, InvalidUserIdException{
		return userService.getAllEmployees();
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<AllEmployeeDTO> getEmployeeById(@PathVariable("id") int id) throws EmployeeNotFoundException, InvalidUserIdException, InvalidEmployeeIdException{
		return userService.getEmployeeById(id);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> modifyEmployeeById(@PathVariable("id") int id, @Valid @RequestBody EditEmployeeDTO e) throws EmployeeNotFoundException, InvalidEmployeeIdException, AccessDeniedException, InvalidUserIdException{
		return userService.modifyEmployeeById(id, e);	
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id) throws EmployeeNotFoundException, InvalidUserIdException, AccessDeniedException, InvalidEmployeeIdException{
		return userService.deleteEmployeeById(id);
	}
	
	@GetMapping("Users")
	public ResponseEntity<List<User>> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PutMapping("changePassword/{id}/{newPassword}")
	public ResponseEntity<Boolean> changePassword(@PathVariable int id, @PathVariable String newPassword){
		return userService.changePassword(id, newPassword);
	}
}
