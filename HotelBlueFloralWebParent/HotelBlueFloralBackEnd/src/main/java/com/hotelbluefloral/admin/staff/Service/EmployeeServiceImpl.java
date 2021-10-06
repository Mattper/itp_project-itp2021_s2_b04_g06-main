package com.hotelbluefloral.admin.staff.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotelbluefloral.comman.entity.Employee;
import com.hotelbluefloral.admin.staff.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long eid) {
		Optional<Employee> optional =employeeRepository.findById(eid);
		Employee employee=null;
		if(optional.isPresent()) {
			employee=optional.get();
		}else {
			throw new RuntimeException("Employee ID not found!");
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long eid) {
		this.employeeRepository.deleteById(eid);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
			Sort.by(sortField).descending();
		Pageable pageable =PageRequest.of(pageNo - 1, pageSize,sort);
		return this.employeeRepository.findAll(pageable);
	}

	
}
