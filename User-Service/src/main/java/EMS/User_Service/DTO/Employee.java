package EMS.User_Service.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
//	Employee: ID, name, department, email, phone, reporting manager 
	private int empId;
	
	@NotEmpty
	private String name;
	
	private String department;
	
	private String email;
	
	@Size(min = 10, max = 10)
	private String phone;
	
	private int reportingManagerId;

	@Override
	public String toString() {
		return "Employee [id=" + empId + ", name=" + name + ", department=" + department + ", email=" + email + ", phone="
				+ phone + ", reportingManager=" + reportingManagerId + "]";
	}
	
	
}

