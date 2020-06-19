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
//    private UserDAO userDao;

//    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserDAO userDao) {
    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
//        this.userDao = userDao;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
//        	.antMatchers("/gcfashions/shop").permitAll()
        	.antMatchers("/gcfashions/shop/**").authenticated()
        	.antMatchers("/gcfashions/shop/**").hasAuthority("ROLE_CUSTOMER")
//        	.antMatchers("/gcfashions/sales/transactions").hasAnyRole("ADMIN", "MANAGER	")
        	.and()
        	.httpBasic();
//                // remove csrf and state in session because in jwt we do not need them
//                .csrf().disable()
//                //removed, we do not want a sticky session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // add jwt filters (1. authentication, 2. authorization)
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//				.addFilter(new JwtAuthorizationFilter(authenticationManager(),
//						this.userDao))
//                .authorizeRequests()
//                // configure access rules
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/gcfashions/shop/products").permitAll()
//                .antMatchers(HttpMethod.PUT, "/gcfashions/new/account").permitAll()
//                .antMatchers("/gcfashions/shop/*").permitAll()
//                .antMatchers("/gcfashions/account/*").hasRole("CUSTOMER")
//                .antMatchers("/gcfashions/sales/*").hasRole("SALES")
//                .antMatchers("/gcfashions/account/*").hasRole("MANAGENT")
//                .anyRequest().authenticated();
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