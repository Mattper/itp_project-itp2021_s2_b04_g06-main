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

import com.hotelbluefloral.comman.entity.Activity;

import com.hotelbluefloral.admin.staff.Service.ActivityService;

@Controller
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	//Display list Of Activity
	@GetMapping("/viewActivityDetails")
	public String showActivity(Model model) {
		return findPaginated(1, "date","desc", model);
	}
	
	//add activity form
	@GetMapping("/showAddActivityForm")
	public String showAddActivityForm(Model model) {
		Activity activity=new Activity();
		model.addAttribute("activity", activity);
		return "activity/addActivity";
	}
	
	@PostMapping("/addActivity")
	public String saveActivity(@ModelAttribute("activity") Activity activity) {
		//Save Activity to DB
		activityService.saveActivity(activity);
		return "mainStaff";
	}
	
	//Activity Update form
	@GetMapping("/showActivityUpdateForm/{acid}")
	public String showActivityUpdateForm(@PathVariable(value="acid")long acid,Model model) {
		
		//get activity from service class
		Activity activity=activityService.getActivityeById(acid);
		
		//set activity as a model attribute to pre-populate form
		model.addAttribute("activity", activity);
		return "activity/updateActivity";
	}
	
	//delete activity
	@GetMapping("/deleteActivity/{acid}")
	public String deleteActivity(@PathVariable(value="acid")long acid) {
		this.activityService.deleteActivityById(acid);
		return "mainStaff";
	}
	
	//Pagination & sorting
		@GetMapping("/actList/{pageNo}")
		public String findPaginated(@PathVariable(value="pageNo")int pageNo,
				@RequestParam("sortField")String sortField,
				@RequestParam("sortDir")String sortDir,
				Model model) {
			int pageSize=5;
			
			Page<Activity> page=activityService.findPaginated(pageNo, pageSize,sortField,sortDir);
			List<Activity> listActivity=page.getContent();
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			
			model.addAttribute("sortField",sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			
			model.addAttribute("listActivity", listActivity);
			return "activity/activityDetails";
		}
	
}
