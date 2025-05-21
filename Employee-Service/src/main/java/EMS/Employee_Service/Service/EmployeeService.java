package EMS.Employee_Service.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import EMS.Employee_Service.Entity.Employee;
import EMS.Employee_Service.Exception.EmployeeNotFoundException;
import EMS.Employee_Service.Repository.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;
	
	public ResponseEntity<Employee> getEmployeeById(int id) {
		Employee e = employeeRepo.findById(id).orElse(null);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}

	public ResponseEntity<Employee> addEmployee(Employee e) {
		Employee e1 = employeeRepo.save(e);
		return new ResponseEntity<>(e1, HttpStatus.CREATED);
	}

	public ResponseEntity<List<Employee>> findByEmployeesByReportingManagerId(int id) {
		List<Employee> e1 = employeeRepo.findByEmployeesByReportingManagerId(id);
		return new ResponseEntity<List<Employee>>(e1, HttpStatus.OK);
		
	}

	public ResponseEntity<Employee> modifyEmployee(Employee e) {
		Employee e1 = employeeRepo.save(e);
		return new ResponseEntity<>(e1, HttpStatus.OK);
	}

	public ResponseEntity<List<Employee>> addAllEmployee(List<Employee> e) {
		List<Employee> e1 = employeeRepo.saveAll(e);
		return new ResponseEntity<>(e1, HttpStatus.OK);
	}

	public ResponseEntity<Employee> deleteEmployee(Employee e) {
		employeeRepo.delete(e);
		return new ResponseEntity<Employee>(e, HttpStatus.OK);
	}

	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> e1 = employeeRepo.findAll();
		return new ResponseEntity<>(e1, HttpStatus.OK);
	}
	
	

}
