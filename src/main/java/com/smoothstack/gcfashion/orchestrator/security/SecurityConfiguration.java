package com.smoothstack.gcfashion.orchestrator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smoothstack.gcfashion.orchestrator.db.UserDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserDAO userDao; 

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserDAO userDao) { 
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.userDao = userDao; //commented out 
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        // remove csrf and state in session because in jwt we do not need them
	        .csrf().disable()
	        //removed, we do not want a sticky session
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        // add jwt filters (1. authentication, 2. authorization)
	        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),
					this.userDao))
	        .authorizeRequests()
	        // configure access rules
	        .antMatchers(HttpMethod.POST, "/login").permitAll()
	        .antMatchers(HttpMethod.POST, "/gcfashions/user").permitAll()
	        .antMatchers(HttpMethod.GET, "/gcfashions/shop/**").permitAll()
	        .antMatchers("/gcfashions/account/**").hasAuthority("ROLE_CUSTOMER")
	        .antMatchers("/gcfashions/sales/**").hasAnyRole("ADMIN", "MANAGER")
	        .antMatchers("/gcfashions/accountant/**").hasAnyRole("MANAGER")
	            .anyRequest().authenticated();
	}

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}