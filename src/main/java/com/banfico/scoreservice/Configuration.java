/*package com.banfico.scoreservice;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.banfico.scoreservice.login.User;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User sessionUser() {
	    return null == user ? new User() : user;
	}
}
*/