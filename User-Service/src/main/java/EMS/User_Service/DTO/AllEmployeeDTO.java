package EMS.User_Service.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import EMS.User_Service.Entity.User;
import EMS.User_Service.Entity.User.Role;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeeDTO {
	
	@Size(min = 8)
	private String userName;
	
	@Size(min = 8)
	private String password;
	
	private int empId;
	private Role role;
	private String name;
	
	private String department;
	
	private String email;
	
	private String phone;
	
	private int reportingManagerId;
	
	@JsonIgnore
	public Employee getEmployee() {
		Employee e = new Employee();
		e.setEmpId(this.empId);
		e.setName(this.name);
		e.setDepartment(this.department);
		e.setEmail(this.email);
		e.setPhone(this.phone);
		return e;
	}
	
	@JsonIgnore
	public User getUser() {
		User u = new User();
		u.setRole(this.role);
		u.setUserName(this.userName);
		u.setPassword(password);
		return u;
	}
	
	public AllEmployeeDTO(Employee e, User u) {
		this.userName = u.getUserName();
		this.password = u.getPassword();
		this.empId = e.getEmpId();
		this.role = u.getRole();
		this.name = e.getName();
		this.department = e.getDepartment();
		this.email = e.getEmail();
		this.phone = e.getPhone();
		this.reportingManagerId = e.getReportingManagerId();
	}
}
