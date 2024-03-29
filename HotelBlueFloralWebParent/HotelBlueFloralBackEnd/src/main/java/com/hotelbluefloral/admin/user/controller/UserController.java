package com.hotelbluefloral.admin.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotelbluefloral.admin.FileUploadUtil;
import com.hotelbluefloral.admin.user.UserNotFoundException;
import com.hotelbluefloral.admin.user.UserService;
import com.hotelbluefloral.admin.user.export.UserExcelExporter;
import com.hotelbluefloral.admin.user.export.UserPdfExporter;
import com.hotelbluefloral.comman.entity.Role;
import com.hotelbluefloral.comman.entity.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return listByPage(1, model,  "firstName", "asc", null);
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			  @PathVariable(name = "pageNum") int pageNum, Model model, 
			  @Param("sortField") String sortField, @Param("sortDir") String sortDir, 
			  @Param("keyword") String keyword
			) {
		System.out.println("Sort Field: " + sortField);
		System.out.println("Sort Order: " + sortDir);
		Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        
		return "customers/customers";
	}
	
	@GetMapping("/customers/new")
	public String  newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		
		User user = new User();
		user.setEnabled(true);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Add New User");
		
		return "customers/customer_form";
	}
	
	@PostMapping("/customers/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, 
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhoto(fileName);
			User savedUser = service.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhoto().isEmpty()) user.setPhoto(null);
			service.save(user);
		}
	
		redirectAttributes.addFlashAttribute("message", "The User has been saved successfully");
		
		return getRedirectURLtoAffectedUser(user);
	}

	private String getRedirectURLtoAffectedUser(User user) {
		String firstPartofEmail = user.getEmail().split("@")[0];
		return "redirect:/customers/page/1?sortField=id&sortDir=asc&keyword=" + firstPartofEmail;
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, 
			RedirectAttributes redirectAttributes) {
		try {
		User user = service.get(id);
		List<Role> listRoles = service.listRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Edit User (ID: " + id + " )");
		model.addAttribute("listRoles", listRoles);
		return "customers/customer_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/customers";
		}
		
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", 
					"The user ID " + id + " has been deleted successfully");
			
			} catch (UserNotFoundException ex) {
				redirectAttributes.addFlashAttribute("message", ex.getMessage());
				
			}
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, 
			 @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		 service.updateUserEnabledStatus(id, enabled);
		 String status = enabled ? "enabled" : "disabled";
		 String message = "The user ID " + id + " has been " + status;
		 redirectAttributes.addFlashAttribute("message", message);
		 return "redirect:/customers";
	}
	
	
	
	@GetMapping("/customers/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listAll(); 
		
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);
		
	}
	
	@GetMapping("/customers/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listAll(); 
		
		UserPdfExporter exporter = new UserPdfExporter();
		exporter.export(listUsers, response);
		
	}
	
	@RequestMapping("/home")
	public String viewHome() {
	    return "home";
	}
	
	@RequestMapping("/foods")
	public String viewFoods() {
	    return "foods";
	}
	

}
