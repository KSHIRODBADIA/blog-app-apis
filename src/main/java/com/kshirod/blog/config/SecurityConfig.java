package com.kshirod.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//import com.kshirod.blog.entities.User;
import com.kshirod.blog.security.CustomUserDetailService;
import com.kshirod.blog.security.JwtAuthenticationEntryPoint;
import com.kshirod.blog.security.JwtAuthenticationFilter;

@Configuration
//@EnableWebSecurity     @EnableMethodSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//1.10.00
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests()
//				.authorizeRequests()
//				.requestMatchers("/api/**").authenticated()
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/v3/api-docs").permitAll()
				.requestMatchers(HttpMethod.GET).permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.builder().username("DURGESH").password(passwordEncoder().encode("DURGESH"))
//				.roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(userDetails);
//	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
	
	@Bean
	public FilterRegistrationBean coresFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 CorsConfiguration corsConfiguration = new CorsConfiguration();
		    corsConfiguration.setAllowCredentials(true);
		    corsConfiguration.addAllowedOriginPattern("*");
		    corsConfiguration.addAllowedHeader("Authorization");
		    corsConfiguration.addAllowedHeader("Content-Type");
		    corsConfiguration.addAllowedHeader("Accept");
		    corsConfiguration.addAllowedMethod("POST");
		    corsConfiguration.addAllowedMethod("GET");
		    corsConfiguration.addAllowedMethod("DELETE");
		    corsConfiguration.addAllowedMethod("PUT");
		    corsConfiguration.addAllowedMethod("OPTIONS");
		    corsConfiguration.setMaxAge(3600L);    
		    source.registerCorsConfiguration("/**", corsConfiguration); 
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		return bean;
	}

}
