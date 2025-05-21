package EMS.Employee_Service.DTO;


import EMS.Employee_Service.Entity.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	private int empId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	@MapsId
	private Employee employee;
	
	@Size(min = 8)
	private String userName;
	
	@Size(min = 8)
	private String password;
	
	private Role role;
	
	
	public enum Role{
		ADMIN,
		HR,
		EMPLOYEE
	}


	@Override
	public String toString() {
		return "User [employee=" + employee + ", userName=" + userName + ", password=" + password + ", role=" + role
				+ "]";
	}
	
}
