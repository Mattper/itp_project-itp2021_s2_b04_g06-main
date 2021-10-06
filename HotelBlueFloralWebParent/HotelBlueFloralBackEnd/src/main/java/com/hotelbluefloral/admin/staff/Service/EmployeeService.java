package com.hotelbluefloral.admin.staff.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hotelbluefloral.comman.entity.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	Employee getEmployeeById(long eid);
	void saveEmployee(Employee employee);
	void deleteEmployeeById(long eid);
	Page<Employee> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection);
}
