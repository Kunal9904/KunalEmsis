package EMS.User_Service.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.transaction.Transactional;
import EMS.User_Service.DTO.*;
import EMS.User_Service.Entity.LoginCredential;
import EMS.User_Service.Entity.User;
import EMS.User_Service.Entity.User.Role;
import EMS.User_Service.Exception.EmployeeNotFoundException;
import EMS.User_Service.Exception.InvalidEmployeeIdException;
import EMS.User_Service.Exception.InvalidUserIdException;
import EMS.User_Service.FeignClient.EmployeeServiceClient;
import EMS.User_Service.JWT.JwtService;
import EMS.User_Service.Repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	EmployeeServiceClient employeeService;
	
	@Transactional
	public ResponseEntity<Employee> addEmployee(AddEmployeeDTO e) throws MethodArgumentNotValidException, EmployeeNotFoundException, InvalidEmployeeIdException, AccessDeniedException, InvalidUserIdException{

		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User u1 = getUserbyName(userName).getBody();
		if(u1.getRole() != Role.ADMIN) {
			throw new AccessDeniedException(u1.getRole()+" Cannot add an Employee");
		}
		Employee manager = null;
		
		if(e.getReportingManagerId() != 0) {
			manager = employeeService.getEmployeeById(e.getReportingManagerId()).getBody();
		}
		if(manager == null && e.getReportingManagerId() != 0) {
			throw new InvalidEmployeeIdException("Manager with manager Id = " + e.getReportingManagerId() + " Does not Exist");
		}
		
		Employee e1 = e.getEmployee();
		e1.setReportingManagerId(e.getReportingManagerId());
		
//		
		
		User u = e.getUser();
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder(12);
		String pass = bcp.encode(u.getPassword());
		u.setPassword(pass);
		User newUser = addUser(u).getBody();
		e1.setEmpId(newUser.getEmpId());
		
		Employee saved = employeeService.addEmployee(e1).getBody(); 

		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	@Transactional
	public ResponseEntity<Employee> modifyEmployeeById(int id, EditEmployeeDTO e) throws EmployeeNotFoundException,InvalidEmployeeIdException, InvalidUserIdException, AccessDeniedException {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User u1 = getUserbyName(userName).getBody();
		if(u1.getRole() != Role.ADMIN) {
			throw new AccessDeniedException(u1.getRole()+" Cannot modify an Employee Details");
		}
		
		Employee fetched = employeeService.getEmployeeById(id).getBody();
		if(fetched == null) {
			throw new InvalidEmployeeIdException("Employee with Employee Id = " + id + " Does not Exist");
		}
		Employee manager = null;
		if(e.getReportingManagerId() != 0) {
			manager = employeeService.getEmployeeById(e.getReportingManagerId()).getBody();
			System.out.println("Manager set");
		}
		if(manager == null && e.getReportingManagerId() != 0) {
			System.out.println("Manager does not exist");
			throw new InvalidEmployeeIdException("Manager with manager Id = " + e.getReportingManagerId() + " Does not Exist");
		}
		
		Employee update = e.getEmployee();
		update.setEmpId(fetched.getEmpId());
		update.setReportingManagerId(e.getReportingManagerId());
		System.out.println(update);
		Employee updated = employeeService.addEmployee(update).getBody();
		System.out.println(updated.toString());
		User u = e.getUser();
		u.setEmpId(id);
		updateUser(u.getEmpId(), u.getRole(), u.getUserName());
		return new ResponseEntity<>(updated, HttpStatus.OK);
		
		
	}
	
	@Transactional
	private boolean updateUser(int empId, Role role, String username) {
		// TODO Auto-generated method stub
		int roleint = 0; 
		
		// converting Role From String to number because it is saved in the database as 0,1,2 rather than admin, Employee , hr
		if(role.equals(Role.ADMIN)) roleint = 0;
		else if(role.equals(Role.HR)) roleint = 1;
		else roleint = 2;
			
		
		int a = userRepo.updateUser(empId, roleint, username);
		if(a > 0) return true;
		else return false;
		
	}

	@Transactional
	public ResponseEntity<String> deleteEmployeeById(int id) throws EmployeeNotFoundException, InvalidUserIdException, AccessDeniedException, InvalidEmployeeIdException {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User u1 = getUserbyName(userName).getBody();
		if(u1.getRole() != Role.ADMIN) {
			throw new AccessDeniedException(u1.getRole()+" Cannot delete an Employee");
		}
		
		
		Employee fetched = employeeService.getEmployeeById(id).getBody();
		if(fetched == null) {
			throw new InvalidEmployeeIdException("Employee with Employee Id = " + id + " Does not Exist");
		}
		fetched.setReportingManagerId(0);
		employeeService.modifyEmployee(fetched);
		List<Employee> list  = employeeService.findByEmployeesByReportingManagerId(fetched.getEmpId()).getBody();
		for(Employee e1 : list) {
			e1.setReportingManagerId(0);
			
		}
		employeeService.addAllEmployee(list);
		User u = getUserById(id).getBody();
		userRepo.delete(u);
		employeeService.deleteEmployee(fetched);
		return new ResponseEntity<>("Employee with name  = " + fetched.getName() + " has been removed from database", HttpStatus.OK);
		
	}
	
	public ResponseEntity<LoginResult> verify(LoginCredential user) throws InvalidUserIdException{
        Authentication authentication= authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
        	LoginResult result = new LoginResult();
        	
        	String token = jwtService.generateToken(user.getUsername());
        	result.setToken(token);
        	User u = getUserbyName(user.getUsername()).getBody();
        	result.setUserRole(u.getRole());
        	result.setEmpId(u.getEmpId());
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else{
            throw new InvalidUserIdException("Invalid username or password");
        }
    }
	

	public ResponseEntity<List<AllEmployeeDTO>> getAllEmployees() throws AccessDeniedException, InvalidUserIdException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User u1 = getUserbyName(userName).getBody();
		if(u1.getRole() != Role.ADMIN && u1.getRole() != Role.HR) {
			throw new AccessDeniedException(u1.getRole()+" Cannot view All Employees");
		}
		List<Employee> eList = employeeService.getAllEmployees().getBody();
		List<User> uList = userRepo.findAll();
		List<AllEmployeeDTO> result = new ArrayList<>();
		for(int i = 0; i < uList.size(); i++) {
			result.add(new AllEmployeeDTO(eList.get(i), uList.get(i)));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	public ResponseEntity<User> addUser(User u){
		User saved = userRepo.save(u);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	public ResponseEntity<User> modifyUser(User u){
		User saved = userRepo.save(u);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	public ResponseEntity<User> getUserById(int userId) throws InvalidUserIdException{
		User u = userRepo.findById(userId).orElse(null);
		if(u != null) {
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}else {
			throw new InvalidUserIdException("The User with userId = " + userId + " does not exist" );
		}
	}
	
	public ResponseEntity<User> getUserbyName(String userName) throws InvalidUserIdException{
		User u =  userRepo.findByUserName(userName);
		if(u != null) {
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}else {
			throw new InvalidUserIdException("The User with userName = " + userName + " does not exist" );
		}
	}

	public ResponseEntity<List<User>> getAllUsers() {
		List<User> u = userRepo.findAll();
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	public ResponseEntity<AllEmployeeDTO> getEmployeeById(int id) throws InvalidUserIdException, InvalidEmployeeIdException {
		Employee e = employeeService.getEmployeeById(id).getBody();
		if(e == null) {
			throw new InvalidEmployeeIdException("Employee with employee Id = " + id + " Does not Exist");
		}
		User u = getUserById(id).getBody();
		AllEmployeeDTO obj = new AllEmployeeDTO(e, u);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<Boolean> changePassword(int id, String newPassword){
		BCryptPasswordEncoder obj = new BCryptPasswordEncoder(12);
		String pass = obj.encode(newPassword);
		int a = userRepo.changePassword(id, pass);
		if(a > 0) return new ResponseEntity<>(true, HttpStatus.OK);
		else return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
