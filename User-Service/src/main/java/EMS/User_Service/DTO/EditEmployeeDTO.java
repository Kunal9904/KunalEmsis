package EMS.User_Service.DTO;

import EMS.User_Service.Entity.User;
import EMS.User_Service.Entity.User.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditEmployeeDTO {
	@Size(min = 8)
	private String userName;
	
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
		u.setRole(role);
		u.setUserName(userName);
		return u;
	}

}
