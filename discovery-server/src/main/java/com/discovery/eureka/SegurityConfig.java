package com.discovery.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity //extends WebSecurityConfigurerAdapter
public class SegurityConfig  {

	@Value("${app.eureka.username}")
	private String username;
	
	@Value("${app.eureka.password}")
	private String password;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(username)
                               .password(passwordEncoder().encode(password))
                               .roles("USER")
                               .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // NoOpPasswordEncoder is used for plain text passwords
    }

	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeHttpRequests().anyRequest()
//		.authenticated()
//		.and()
//		.httpBasic()
//	}
	
}
