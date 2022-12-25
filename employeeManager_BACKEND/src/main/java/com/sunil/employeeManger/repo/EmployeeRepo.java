package com.sunil.employeeManger.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.employeeManger.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
