package com.sunil.employeeManger.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sunil.employeeManger.exception.UserNotFoundException;
import com.sunil.employeeManger.model.Employee;
import com.sunil.employeeManger.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public Employee addEmployee(Employee employee) {
		employee.setEmpoyeeCode(UUID.randomUUID().toString());
		return employeeRepo.save(employee);
	}

//	public List<Employee> findAllEmployee(Integer pageNumber, Integer pageSize, String field) throws UserNotFoundException {
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field));
//		Page<Employee> allPage = employeeRepo.findAll(pageable);
//		
//		List<Employee> allEmployess = allPage.getContent();
//		
//		if (allEmployess.isEmpty())
//			throw new UserNotFoundException("No employeess found!! \nTry less page number or page size.");
//		
//		return allEmployess;
//	}

	public Page<Employee> findAllEmployee(Integer pageNumber, Integer pageSize, String field)
			throws UserNotFoundException {

		Pageable pageable = PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field));
		Page<Employee> allPage = employeeRepo.findAll(pageable);

		if (allPage.isEmpty())
			throw new UserNotFoundException("No employeess found!! \nTry less page number or page size.");

		return allPage;
	}

	public Employee updateEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}

	public void deleteEmployee(Long id) throws Exception {
		try {
			employeeRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw e;
		}
	}

	public Employee findEmployeeById(Long id) throws UserNotFoundException {
		return employeeRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!!"));
	}

}
