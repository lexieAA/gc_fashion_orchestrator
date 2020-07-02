package com.smoothstack.gcfashion.orchestrator.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smoothstack.gcfashion.orchestrator.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    
	private static final long serialVersionUID = -4854689302247698597L;
	private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

//        // Extract list of permissions (name)
//        this.user.getPermissionList().forEach(p -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority(p);
//            authorities.add(authority);
//        });

        // Extract list of roles (ROLE_name)
        String r = this.user.getRole().toUpperCase();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
        authorities.add(authority);

        return authorities;
    }
    
    public String getUserId() {
    	return Long.toString(this.user.getUserId());  	
    }

    @Override
    public String getPassword() {
    	try {
    		return this.user.getPassword();
    	}catch(Error e) {
    		return "password get error " + e;
    	}
        
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }
    
    public String getRole() {
        return this.user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public boolean isEnabled() {
		return true; // set from false to true
	}
//
//    @Override
//    public boolean isEnabled() {
//        return this.user.getActive() == 1;
//    }
}