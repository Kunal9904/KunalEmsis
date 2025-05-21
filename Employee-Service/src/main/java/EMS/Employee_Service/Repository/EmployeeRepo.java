package EMS.Employee_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import EMS.Employee_Service.Entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

	@Query(value = "select * from employee where manager_id = ?1 ", nativeQuery = true)
	List<Employee> findByEmployeesByReportingManagerId(int id);

}
