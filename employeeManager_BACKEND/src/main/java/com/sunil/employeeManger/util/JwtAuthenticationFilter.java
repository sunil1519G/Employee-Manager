package com.sunil.employeeManger.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunil.employeeManger.service.AppUserServiceImpl;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AppUserServiceImpl userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		String jwtToken = null;
		String username = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

			jwtToken = authorizationHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwtToken);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UserDetails userDetails = userService.loadUserByUsername(username);

					if (jwtUtil.validateToken(jwtToken, userDetails)) {

						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authenticationToken.setDetails(new WebAuthenticationDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					}
				}
				
			} catch (SignatureException e) {
				System.out.println("\nError: "+e.getMessage()+"\n"+e);	// change with Logback
			}
		}

		filterChain.doFilter(request, response);
	}

}
