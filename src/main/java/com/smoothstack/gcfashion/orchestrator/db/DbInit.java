package com.smoothstack.gcfashion.orchestrator.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smoothstack.gcfashion.orchestrator.entity.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
  private PasswordEncoder passwordEncoder;

  public DbInit(PasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
  }
	
	@Autowired
	UserDAO uDAO;
  
	public Integer saveUser(User user) {
//		if (user.getUsername() != null && user.getPassword() != null) {
			try {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				System.out.print(user.getPassword());
				// create the new record
				uDAO.save(user);
			} catch (Exception e) {
				// query error
				return -1;
			}
//		}
//
		return 0;
	}

  @Override
  public void run(String... args) {
	  
      // Create users
      User dan = new User(2L, 1234567890123456d, "Matt",passwordEncoder.encode("123"),"SALES","Matt","nowhere");
//      User admin = new User("kim",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_USERS");
//      User manager = new User("borrower",passwordEncoder.encode("borrower123"),"BORROWER","ACCESS_B1, ACCESS_B2, ACCESS_B3, ACCESS_B4, ACCESS_B5, ACCESS_B6, ACCESS_B7, ACCESS_B8");

     this.uDAO.save(dan);
//      List<User> users = Arrays.asList(dan);

      // Save to database
//      this.userDao.saveAll(users);
  }
}
