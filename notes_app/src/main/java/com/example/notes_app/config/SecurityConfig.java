package com.example.notes_app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService myUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//disabling csrf using Lamba syntax
		http.csrf(customizer -> customizer.disable());
		http.authorizeHttpRequests(request -> request
//				.anyRequest().permitAll()
				.requestMatchers("/users/checkUsername","/users/register","/notes/fetchAll")
				.permitAll()
				.anyRequest().authenticated());
		
		//For enabling Rest Access with default username and password -> Basic Authentication
		http.httpBasic(Customizer.withDefaults());
		http.cors();
		// Lets make our authentication stateless what this will do is every time a new request comes it will give a different session id.. this will ensure that no one else is able to forge your session id and make request on your behalf
		// Now since we make it stateless we will need to have a token based authentication each time a new request comes it will have to get the token with it which we will validate at our backend side before sending an appropriate response
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
	
	// if u want the authentication details for user to come from database u need a midleware called a Authentication Provider this interacts with the database
	// Un-authenticated Object (from request) -> Authentication Provider -> Authenticated Object (in response)
	@Bean
	public AuthenticationProvider authenticationProvider() {
		// Data Access Object for Authentication Provider
		// Now for fetching the data we definately need a service class that interacts with the repository layer to fetch the data. So we will pass that service class to our DaoAuthenticationProvider
		
		// Following syntax is depricated:
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(myUserDetailsService);
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:5173")); // your frontend origin
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    config.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
	
}
