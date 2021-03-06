package com.smoothstack.gcfashion.orchestrator.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smoothstack.gcfashion.orchestrator.db.UserDAO;
import com.smoothstack.gcfashion.orchestrator.entity.User;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserDAO userDAO;

    public UserPrincipalDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userDAO.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}