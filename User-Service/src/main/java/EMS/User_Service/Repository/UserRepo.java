package EMS.User_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import EMS.User_Service.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	User findByUserName(String userName);

	@Modifying
	@Query(value = "update user set role = ?2,user_name = ?3 where emp_id = ?1 ", nativeQuery= true)
	int updateUser(int empId, int roleint, String username);

	@Modifying	
	@Query(value = "update user set password = ?2 where emp_id = ?1", nativeQuery = true)
	int changePassword(int id, String newPassword);

}

