package com.hotelbluefloral.admin.staff.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbluefloral.comman.entity.Attendance;
import com.hotelbluefloral.admin.staff.Service.AttendanceService;

@Controller
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	//Mapping to MainStaff page
	@GetMapping("/showMainStaff")
	public String showMainStaffpage() {
		return "mainStaff";
	}
	
	//Display list of attendance
	@GetMapping("/viewAttendanceDetails")
	public String viewpage(Model model) {
		return findPaginated(1,"date","desc", model);
	}
	
	//add attendance form
	@GetMapping("/showAddAttendanceForm")
	public String showAddAttendanceForm(Model model) {
		Attendance attendance=new Attendance();
		model.addAttribute("attendance", attendance);
		return "attendance/addAttendance";
	}
	
	@PostMapping("/addAttendance")
	public String saveAttendace(@ModelAttribute("attendance") Attendance attendance) {
		//Save Attendance to DB
		attendanceService.saveAttendance(attendance);
		return "mainStaff";
	}
	
	//Attendance Update form
	@GetMapping("/showAttendanceUpdateForm/{aid}")
	public String showAttendanceUpdateForm(@PathVariable(value="aid")long aid,Model model) {
		
		//get attendance from service class
		Attendance attendance=attendanceService.getAttendanceById(aid);
		
		//set attendance as a model attribute to Pre-populate  form
		model.addAttribute("attendance", attendance);
		return "attendance/updateAttendance";
	}
	
	//Delete attendance
	@GetMapping("/deleteAttendance/{aid}")
	public String deleteAttendance(@PathVariable(value="aid")long aid) {
		this.attendanceService.deleteAttendanceById(aid);
		return "mainStaff";
	}
	
	//Pagination & sorting
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo")int pageNo,
			@RequestParam("sortField")String sortField,
			@RequestParam("sortDir")String sortDir,
			Model model) {
		int pageSize=5;
		
		Page<Attendance> page=attendanceService.findPaginated(pageNo, pageSize,sortField,sortDir);
		List<Attendance> listAttendance=page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listAttendance", listAttendance);
		return "attendance/attendanceDetails";
	}
	
}








