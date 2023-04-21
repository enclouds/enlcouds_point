package com.enclouds.enpoint.config;

import com.enclouds.enpoint.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * LoggerInteceptor Bean에 등록.
 * @author Enclouds
 * @since 2020.12.11
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
				.excludePathPatterns("/**");
				
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/resources/");
	}

	//	@Bean
//	public Filter characterEncodingFilter() {
//
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		return characterEncodingFilter;
//
//	}
//
//	public HttpMessageConverter<String> responseBodyConverter(){
//		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//
//	}
	
}
