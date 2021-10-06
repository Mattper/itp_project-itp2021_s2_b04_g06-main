package com.hotelbluefloral.admin.staff.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotelbluefloral.comman.entity.Attendance;
import com.hotelbluefloral.admin.staff.Repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Override
	public List<Attendance> getAttendance(){
		return attendanceRepository.findAll();
	}

	@Override
	public void saveAttendance(Attendance attendance) {
		this.attendanceRepository.save(attendance);
	}

	@Override
	public Attendance getAttendanceById(long aid) {
		Optional<Attendance> optional = attendanceRepository.findById(aid);
		Attendance attendance=null;
		if(optional.isPresent()) {
			attendance=optional.get();
		}else {
			throw new RuntimeException("Attendance Id not found!");
		}
		return attendance;
	}

	@Override
	public void deleteAttendanceById(long aid) {
		this.attendanceRepository.deleteById(aid);
	}

	@Override
	public Page<Attendance> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection) {
		Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
			Sort.by(sortField).descending();
		Pageable pageable =PageRequest.of(pageNo - 1, pageSize,sort);
		return this.attendanceRepository.findAll(pageable);
	} 
}
