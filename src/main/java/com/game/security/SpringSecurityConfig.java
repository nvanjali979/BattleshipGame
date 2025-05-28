package com.game.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security class for authenticating the request
 * Basic auth is used here
 */
@Configuration
public class SpringSecurityConfig {

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.requestMatchers(HttpMethod.POST, "/game/*").authenticated()
		.anyRequest().permitAll()
		.and()
		.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails playerOne = User.builder()
				.username("playerOne")
				.password(passwordEncoder().encode("playerOne"))
				.roles("USER").build();

		UserDetails playerTwo = User.builder()
				.username("playerTwo")
				.password(passwordEncoder().encode("playerTwo"))
				.roles("USER").build();

		return new InMemoryUserDetailsManager(playerOne, playerTwo);
	}
}
