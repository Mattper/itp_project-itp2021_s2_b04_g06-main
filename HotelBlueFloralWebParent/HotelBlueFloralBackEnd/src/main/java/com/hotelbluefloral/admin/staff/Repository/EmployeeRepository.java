package com.hotelbluefloral.admin.staff.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotelbluefloral.comman.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	@Query("SELECT e FROM employee e WHERE e.eid LIKE %?1%")
	public static long searchEmployeeId(long eid) {
		return eid;
	}
}
