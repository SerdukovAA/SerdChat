package com.serd.chat.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;


	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select email,password, true from users where email=?")
				.authoritiesByUsernameQuery(
						"select u.email, ur.user_role_name from users u, user_role ur where u.user_role_id = ur.user_role_id and email=?");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.authorizeRequests()
			.antMatchers("/chat**").access("hasAnyRole('USER','ADMIN')")
			.antMatchers("/admin**").access("hasRole('ADMIN')")
			.and()
				.formLogin().loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/login?error")

				.usernameParameter("username").passwordParameter("password")
			.and()
				.logout().logoutSuccessUrl("/login?logout")
.and()
		.exceptionHandling().accessDeniedPage("/403")
		.and()
		.csrf();
		//.disable()
	//	;


		}
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}