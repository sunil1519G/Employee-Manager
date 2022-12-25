package com.sunil.employeeManger.service;

import org.springframework.data.domain.Page;

import com.sunil.employeeManger.exception.UserNotFoundException;
import com.sunil.employeeManger.model.Employee;

public interface EmployeeService {
	
	public Employee addEmployee(Employee employee);

	//public List<Employee> findAllEmployee(Integer pageNumber, Integer pageSize, String sortField) throws Exception;

	public Page<Employee> findAllEmployee(Integer pageNumber, Integer pageSize, String sortField) throws Exception;
	
	public Employee updateEmployee(Employee employee);

	public void deleteEmployee(Long id) throws Exception;

	public Employee findEmployeeById(Long id) throws UserNotFoundException;
}
