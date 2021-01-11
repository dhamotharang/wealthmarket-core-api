package com.wm.core;

import com.wm.core.config.RequestFilterConfig;
import com.wm.core.filter.RequestFilter;
import com.wm.core.service.authmanager.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication()
public class WealthmarketCoreApplication {

	Logger logger = LoggerFactory.getLogger(WealthmarketCoreApplication.class.getName());

	@Value("${cors.url}")
	String urls;

	public static void main(String[] args) {
		SpringApplication.run(WealthmarketCoreApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		String [] origins = urls.split(",");
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(origins);
			}
		};
	}

	@Bean
	public FilterRegistrationBean<RequestFilter> requestSignatureFilterFilterRegistrationBean(
			RequestFilterConfig requestSignatureFilterConfig, AuthenticationService authenticationService) {
		RequestFilter requestSignatureFilter = new RequestFilter(requestSignatureFilterConfig, authenticationService);
		FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(requestSignatureFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.setAsyncSupported(false);
		return registrationBean;
	}
}