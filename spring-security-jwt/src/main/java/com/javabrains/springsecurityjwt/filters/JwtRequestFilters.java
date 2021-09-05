package com.javabrains.springsecurityjwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javabrains.springsecurityjwt.services.JwtUtils;
import com.javabrains.springsecurityjwt.services.MyUserDetailsService;

@Component
public class JwtRequestFilters extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizarionHeader = request.getHeader("authorization");
		String userName = null;
		String jwt = null;

		if (authorizarionHeader != null && authorizarionHeader.startsWith("Bearer ")) {
			jwt = authorizarionHeader.substring(7);
			userName = jwtUtils.extractUserName(jwt);
			System.out.println("UserName from filter::::" + userName);
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() ==null) {
			UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(userName);
			if(jwtUtils.validToken(jwt, userDetails)) {
				
				UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			  passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			  SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
			}
			
			
			
		}
		filterChain.doFilter(request, response);
		
	}

}
