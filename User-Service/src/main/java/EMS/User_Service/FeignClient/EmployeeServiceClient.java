package EMS.User_Service.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import EMS.User_Service.DTO.Employee;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface EmployeeServiceClient {

    @GetMapping("/getEmployeeById/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id);

    @PostMapping("/addEmployee")
    ResponseEntity<Employee> addEmployee(@RequestBody Employee e);

    @PutMapping("/modifyEmployee")
    ResponseEntity<Employee> modifyEmployee(@RequestBody Employee e);

    @GetMapping("/findByEmployeesByReportingManagerId/{id}")
    ResponseEntity<List<Employee>> findByEmployeesByReportingManagerId(@PathVariable("id") int id);

    @PostMapping("/addAllEmployee")
    ResponseEntity<List<Employee>> addAllEmployee(@RequestBody List<Employee> e);

    @DeleteMapping("/deleteEmployee")
    ResponseEntity<Employee> deleteEmployee(@RequestBody Employee e);

    @GetMapping("/getAllEmployees")
    ResponseEntity<List<Employee>> getAllEmployees();
}
