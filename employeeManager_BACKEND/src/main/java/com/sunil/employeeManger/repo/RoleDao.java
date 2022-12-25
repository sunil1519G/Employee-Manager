package com.sunil.employeeManger.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.employeeManger.model.AppUserRole;

public interface RoleDao extends JpaRepository<AppUserRole, Long>{

	AppUserRole findByName(String name);

}
