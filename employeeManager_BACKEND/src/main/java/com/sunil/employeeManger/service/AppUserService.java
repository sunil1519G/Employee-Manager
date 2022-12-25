package com.sunil.employeeManger.service;

import java.util.List;

import com.sunil.employeeManger.model.AppUser;
import com.sunil.employeeManger.model.AppUserRole;

public interface AppUserService {

	AppUser saveUser(AppUser user);
	AppUserRole saveRole(AppUserRole role);
	void assignRoleToUser(String username, String roleName);
	AppUser getUser(String username);
	List<AppUser> getUsers();
}
