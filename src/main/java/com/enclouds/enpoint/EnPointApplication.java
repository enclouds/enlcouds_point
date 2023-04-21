package com.enclouds.enpoint;

import com.enclouds.enpoint.config.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
@EnableScheduling
public class EnPointApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EnPointApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(EnPointApplication.class);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}
}
