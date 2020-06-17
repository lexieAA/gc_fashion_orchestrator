package com.smoothstack.gcfashion.orchestrator.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.smoothstack.gcfashion.orchestrator.db.UserDAO;
import com.smoothstack.gcfashion.orchestrator.entity.User;

@RestController
@RequestMapping("/gcfashions")
public class SecurityControlledAPIs {
	//private UserDAO userDao;

	@Autowired
	RestTemplate restTemplate;

//	public SecurityControlledAPIs(UserDAO userDao) {
//		this.userDao = userDao;
//	}

	public static final int ONLINE_SERVICE = 8081;

	// ------------Requests for Online Stores-----------------------
	// read all products
	@GetMapping(path = "/shop/products")
	public ResponseEntity<String> allProducts() throws SQLException {
		return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/products", String.class);
	}

//	// read by branch Id
//	@GetMapping("/lms/public/library/branches/branch/{branchId}")
//	public ResponseEntity<String> branch2(@PathVariable Long branchId) throws SQLException {
//		return restTemplate.getForEntity("http://LIBRARY-SERVICE/lms/public/library/branches/branch/" + branchId,
//				String.class);
//	}

	// update all branches
//	@PutMapping("/lms/public/library/branches/branch/{branchId}")
//	public void branch3(@RequestBody LibraryBranch libraryBranch, @PathVariable Long branchId) throws SQLException {
//		restTemplate.put("http://LIBRARY-SERVICE/lms/public/library/branches/branch/" + branchId, libraryBranch,
//				String.class);
//	}


	/// ------------ Requests for Borrower --------------------------------


}
