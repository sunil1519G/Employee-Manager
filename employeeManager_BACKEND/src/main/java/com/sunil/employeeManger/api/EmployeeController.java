package com.sunil.employeeManger.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.employeeManger.exception.UserNotFoundException;
import com.sunil.employeeManger.model.Employee;
import com.sunil.employeeManger.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping()
	public ResponseEntity<?> home() {
		String message = "Welcome to Employee Management App!!";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<Page<Employee>> getAllEmployee(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField) throws Exception {
		//System.out.println("page number : "+pageNumber+"\npage size : "+pageSize+"\nfield : "+sortField);
		Page<Employee> employees = employeeService.findAllEmployee(pageNumber, pageSize, sortField);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") Long id) throws UserNotFoundException {
		Employee employee = employeeService.findEmployeeById(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
		System.out.println("Saving Employee...");
		Employee newEmployee = employeeService.addEmployee(employee);
		return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
		Employee updatedEmployee = employeeService.updateEmployee(employee);
		return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id) throws Exception {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
