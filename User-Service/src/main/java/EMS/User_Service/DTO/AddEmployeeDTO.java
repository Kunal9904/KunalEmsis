package EMS.User_Service.DTO;

import EMS.User_Service.Entity.User;
import EMS.User_Service.Entity.User.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddEmployeeDTO {
	@Size(min = 8)
	private String userName;
	
	@Size(min = 8)
	private String password;
	
	private Role role;
	
	@NotEmpty
	private String name;
	
	private String department;
	
	private String email;
	
	@Size(min = 10, max = 10)
	private String phone;
	
	private int reportingManagerId;
	
	public Employee getEmployee() {
		Employee e = new Employee();
		e.setDepartment(department);
		e.setEmail(email);
		e.setName(name);
		e.setPhone(phone);
		
		return e;
	}
	
	public User getUser() {
		User u = new User();
		u.setPassword(password);
		u.setRole(role);
		u.setUserName(userName);
		return u;
	}
	
	
	
	

	@Override
	public String toString() {
		return "AddEmployeeDTO [userName=" + userName + ", password=" + password + ", role=" + role + ", name=" + name
				+ ", department=" + department + ", email=" + email + ", phone=" + phone + ", reportingManager="
				+ reportingManagerId + "]";
	}
	
	
}