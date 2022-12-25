package com.sunil.employeeManger.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sunil.employeeManger.dto.AuthRequest;
import com.sunil.employeeManger.model.AppUser;
import com.sunil.employeeManger.model.AppUserRole;
import com.sunil.employeeManger.service.AppUserServiceImpl;
import com.sunil.employeeManger.util.JwtUtil;

@RestController
@RequestMapping("/app")
public class AppUserResource {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) throws Exception {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());

		try {
			authenticationManager.authenticate(authToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username and password", e);
		}

		UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
		
		String jwtToken = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(jwtToken);
	}

	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
		URI url = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/app/user/save").toUriString());

		return ResponseEntity.created(url).body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<AppUserRole> saveRole(@RequestBody AppUserRole role) {
		URI url = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/app/role/save").toUriString());
		return ResponseEntity.created(url).body(userService.saveRole(role));
	}

	@PostMapping("/role/assigntouser")
	public ResponseEntity<?> assignToUser(@RequestBody RoleToUserForm form) {

		return ResponseEntity.ok().build();
	}

}

class RoleToUserForm {
	private String username;
	private String roleName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
