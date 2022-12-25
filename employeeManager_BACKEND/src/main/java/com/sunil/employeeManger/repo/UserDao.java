package com.sunil.employeeManger.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.employeeManger.model.AppUser;

public interface UserDao extends JpaRepository<AppUser, Long>{

	AppUser findByUsername(String username);
}
