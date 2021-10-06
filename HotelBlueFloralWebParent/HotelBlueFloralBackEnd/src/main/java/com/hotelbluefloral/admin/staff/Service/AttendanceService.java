package com.hotelbluefloral.admin.staff.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hotelbluefloral.comman.entity.Attendance;

public interface AttendanceService {
	List<Attendance> getAttendance();
	void saveAttendance(Attendance attendance);
	Attendance getAttendanceById(long aid);
	void deleteAttendanceById(long aid);
	Page<Attendance> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection);
}