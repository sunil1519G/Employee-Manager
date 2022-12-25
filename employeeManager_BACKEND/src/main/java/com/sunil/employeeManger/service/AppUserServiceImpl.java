package com.sunil.employeeManger.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunil.employeeManger.model.AppUser;
import com.sunil.employeeManger.model.AppUserRole;
import com.sunil.employeeManger.repo.RoleDao;
import com.sunil.employeeManger.repo.UserDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

	private final UserDao userDao;
	private final RoleDao roleDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	Logger log = LogManager.getLogger(AppUserServiceImpl.class);

	public AppUserServiceImpl(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userDao.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("User not found in the database");
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			});
		
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public AppUser saveUser(AppUser user) {
		log.info("Saving User {} to the database", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		AppUser savedUser = userDao.save(user);

		return savedUser;
	}

	@Override
	public AppUserRole saveRole(AppUserRole role) {
		log.info("Saving Role {} to the database", role.getName());
		return roleDao.save(role);
	}

	@Override
	public void assignRoleToUser(String username, String roleName) {

		log.info("Assigning Role {} to User {}", roleName, username);
		AppUser user = userDao.findByUsername(username);
		AppUserRole role = roleDao.findByName(roleName);

		user.getRoles().add(role);
	}

	@Override
	public AppUser getUser(String username) {

		log.info("Fetching User {}", username);
		return userDao.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {

		log.info("Fetching all Users");
		return userDao.findAll();
	}

}
