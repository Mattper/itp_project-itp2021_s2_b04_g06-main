package com.hotelbluefloral.admin.staff.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbluefloral.comman.entity.Employee;
import com.hotelbluefloral.admin.staff.Service.EmployeeService;
import com.hotelbluefloral.admin.staff.pdfExport.employeePDFExporter;
import com.lowagie.text.DocumentException;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	//display list of employees
	@GetMapping("/viewEmployeeList")
	public String viewEmployeeList(Model model) {
		return findPaginated(1,"eid","asc", model);
	}
	
	//display employee details by getting employee id
	@GetMapping("/viewEmployeeDetails/{eid}")
	public String viewEmployeeDetails(@PathVariable(value="eid")long eid,Model model) {
		Employee employee =employeeService.getEmployeeById(eid);
		model.addAttribute("employee", employee);
		return "employee/employeeDetails"; 
	}
	
	//display add employee form(GET)
	@GetMapping("/showAddEmployeeForm")
	public String showAddEmployeeForm(Model model) {
		Employee employee=new Employee();
		model.addAttribute("employee", employee);
		return "employee/addEmployee";
	}
	
	@PostMapping("/addEmployee")
	public String saveEmployee(@ModelAttribute("employee")Employee employee) {
		employeeService.saveEmployee(employee);
		return "mainStaff";
	}
	
	//display employee update form and pre-populating form
	@GetMapping("/showEmployeeUpdateForm/{eid}")
	public String showEmployeeUpdateForm(@PathVariable(value="eid")long eid,Model model) {
		Employee employee =employeeService.getEmployeeById(eid);
		model.addAttribute("employee", employee);
		return "employee/updateEmployee";
	}
	
	//delete employee
	@GetMapping("/deleteEmployee/{eid}")
	public String deleteEmployee(@PathVariable(value="eid")long eid) {
		this.employeeService.deleteEmployeeById(eid);
		return "mainStaff";
	}
	
	
	//Employee Search
	@GetMapping("/searchEmployeeList")
	public String searchEmployeeList(Model model,@Param("eid")long eid) {
		Employee employee =employeeService.getEmployeeById(eid);
		model.addAttribute("employee", employee);
		return "employee/employeeDetails"; 
	}
	
	//Pagination & sorting
		@GetMapping("/empList/{pageNo}")
		public String findPaginated(@PathVariable(value="pageNo")int pageNo,
				@RequestParam("sortField")String sortField,
				@RequestParam("sortDir")String sortDir,
				Model model) {
			int pageSize=5;
			
			Page<Employee> page=employeeService.findPaginated(pageNo, pageSize,sortField,sortDir);
			List<Employee> listEmployee=page.getContent();
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			
			model.addAttribute("sortField",sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			
			model.addAttribute("listEmployee", listEmployee);
			return "employee/employeeList";
		}
		
	//Employee Export
		@GetMapping("/employee/employeeExport")
		public void employeeExport(HttpServletResponse response) throws DocumentException, IOException {
			response.setContentType("application/pdf");
			
			DateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime= dateFormatter.format(new Date());
			
			String headerKey ="Content-Disposition";
			String headerValue ="attachment; filename =employees_" +currentDateTime+ ".pdf";
			
			response.setHeader(headerKey, headerValue);
			
			List<Employee> listEmployee =employeeService.getAllEmployees();
			employeePDFExporter exporter = new employeePDFExporter(listEmployee);
			exporter.export(response);
		}
	
}
