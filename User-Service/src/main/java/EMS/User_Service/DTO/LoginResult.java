package EMS.User_Service.DTO;

import EMS.User_Service.Entity.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
	private String token;
	private Role userRole;
	private int empId;
}

