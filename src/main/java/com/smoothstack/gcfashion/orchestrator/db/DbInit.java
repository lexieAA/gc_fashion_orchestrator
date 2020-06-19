package com.smoothstack.gcfashion.orchestrator.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smoothstack.gcfashion.orchestrator.entity.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
  private UserDAO userDao;
  private PasswordEncoder passwordEncoder;

  public DbInit(UserDAO userDao, PasswordEncoder passwordEncoder) {
      this.userDao = userDao;
      this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
	  
      // Create users
      User dan = new User(1L, 1234567890123456d, "jdoe",passwordEncoder.encode("123"),"customer","John Doe","Fairfax, VA");
//      User admin = new User("kim",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_USERS");
//      User manager = new User("borrower",passwordEncoder.encode("borrower123"),"BORROWER","ACCESS_B1, ACCESS_B2, ACCESS_B3, ACCESS_B4, ACCESS_B5, ACCESS_B6, ACCESS_B7, ACCESS_B8");

      this.userDao.save(dan);
//      List<User> users = Arrays.asList(dan);

      // Save to database
//      this.userDao.saveAll(users);
  }
}