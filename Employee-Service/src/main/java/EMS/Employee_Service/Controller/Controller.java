package EMS.Employee_Service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EMS.Employee_Service.Entity.Employee;
import EMS.Employee_Service.Service.EmployeeService;

@RestController
public class Controller {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("getEmployeeById/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping("addEmployee")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee e){
		return employeeService.addEmployee(e);
	}
	
	@PutMapping("modifyEmployee")
	public ResponseEntity<Employee> modifyEmployee(@RequestBody Employee e){
		return employeeService.modifyEmployee(e);
	}
	
	@GetMapping("findByEmployeesByReportingManagerId/{id}")
	public ResponseEntity<List<Employee>> findByEmployeesByReportingManagerId(@PathVariable int id){
		return employeeService.findByEmployeesByReportingManagerId(id);
	}
	
	@PostMapping("addAllEmployee")
	public ResponseEntity<List<Employee>> addAllEmployee(@RequestBody List<Employee> e){
		return employeeService.addAllEmployee(e);
	}
	
	@DeleteMapping("deleteEmployee")
	public ResponseEntity<Employee> deleteEmployee(@RequestBody Employee e){
		return employeeService.deleteEmployee(e);
	}
	
	@GetMapping("getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return employeeService.getAllEmployees();
		
	}

}
