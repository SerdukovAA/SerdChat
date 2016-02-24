package com.serd.chat.config;

import com.serd.chat.dao.*;
import com.serd.chat.model.Message;
import com.serd.chat.model.User;
import com.serd.chat.soket.MessageDecoder;
import com.serd.chat.util.RegistrationUtil;
import com.serd.chat.util.RegistrationUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.serd.chat" })
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/resource/");

	}

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5300000);
		return multipartResolver;
	}



	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/data_base1?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("12345");

		return dataSource;
	}

	@Bean(name="InjectBeans")
	public UserDAO getUserDAO() {
		return new UserDAOImpl(getDataSource());
	}
	@Bean
	public RoomDAO getRoomDAO() {
		return new RoomDAOImpl(getDataSource());
	}
	@Bean
	public MessageDAO getMessageDAO() {
		return new MessageDAOImpl(getDataSource());
	}
	@Bean
	public RegistrationUtil getRegistrationUtil() {
		return new RegistrationUtilImpl();
	}

}