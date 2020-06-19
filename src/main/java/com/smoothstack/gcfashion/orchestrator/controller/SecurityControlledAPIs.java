package com.smoothstack.gcfashion.orchestrator.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smoothstack.gcfashion.orchestrator.entity.Transaction;
import com.smoothstack.gcfashion.orchestrator.db.UserDAO;
import com.smoothstack.gcfashion.orchestrator.entity.User;

@RestController
@RequestMapping("/gcfashions")
public class SecurityControlledAPIs {
	private UserDAO userDao;

	@Autowired
	RestTemplate restTemplate;

	public SecurityControlledAPIs(UserDAO userDao) {
		this.userDao = userDao;
	}

	public static final int ONLINE_SERVICE = 8081;
	public static final int SALES_SERVICE = 8082;
	public static final int ACCOUNT_SERVICE = 8083;

	// ------------Requests for Online Stores-----------------------
		// read all products
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping(path = "/shop/products")
		public ResponseEntity<String> allProducts() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/products", String.class);
		}

		// read by products by Id
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping("/shop/products/{productId}")
		public ResponseEntity<String> allProductsByIdOnline(@PathVariable Long productId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/products/" + productId,
					String.class);
		}
		
		// read all coupons
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping(path = "/shop/coupons")
		public ResponseEntity<String> allCouponsOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/coupons", String.class);
		}
		
		// read by transaction by Id
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping("/account/users/{userId}/transactions")
		public ResponseEntity<String> allTransactionsByIdOnline(@PathVariable Long userId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions/" + userId,
					String.class);
		}

		 //update a transaction
		@CrossOrigin(origins="http://localhost:8080")
		@PostMapping("/account/users/{userId}/transactions")
		public void updateTransactionsByIdOnline(@RequestBody Transaction transaction, @PathVariable Long userId) throws SQLException {
			restTemplate.postForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions/" + userId, transaction,
					String.class);
		}
		
		//create a transaction
		@CrossOrigin(origins="http://localhost:8080")
		@PutMapping("/account/users/{userId}/transactions")
		public void createTransactionsByIdOnline(@RequestBody Transaction transaction) throws SQLException {
			restTemplate.put("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions", transaction,
					String.class);
		}
		
		// read all categories
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping(path = "/shop/categories")
		public ResponseEntity<String> allCategoriesOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/categories", String.class);
		}
		
		// read by products by category Id
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping("/shop/categories/{categoryId}/products")
		public ResponseEntity<String> CategoriesByIdOnline(@PathVariable Long categoryId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/categories/" + categoryId +"/products",
					String.class);
		}
		
		// read all subcategories
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping(path = "/shop/subcategories")
		public ResponseEntity<String> allSubcategoriesOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/subcategories", String.class);
		}
		
		// read all stores
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping(path = "/shop/stores")
		public ResponseEntity<String> allStoresOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/stores", String.class);
		}
		
		// read by users by user Id
		@CrossOrigin(origins="http://localhost:8080")
		@GetMapping("/shop/account/users/{userId}")
		public ResponseEntity<String> userByIdOnline(@PathVariable Long userId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/account/users/" + userId,
					String.class);
		}

		@CrossOrigin(origins="http://localhost:8080")
		@PostMapping("/shop/account/users/{userId}")
		public void updateUserByIdOnline(@RequestBody User user, @PathVariable Long userId) throws SQLException {
			restTemplate.postForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/account/users/" + userId, user,
					String.class);
		}

		@CrossOrigin(origins="http://localhost:8080")
		@PutMapping("/new/account")
		public void createUserByIdOnline(@RequestBody User user, @PathVariable Long userId) throws SQLException {
			restTemplate.put("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/account/users", user,
					String.class);
		}
		
	// ------------ Requests for Sales --------------------------------


	// read a transaction by id
	@CrossOrigin(origins="http://localhost:8080")
	@GetMapping(path = "/sales/transactions/{id}")
	public ResponseEntity<String> getTransactionById(@PathVariable Long id) {
		return restTemplate.getForEntity("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions/" + id,
				String.class);
	}

	// update a transaction
	@CrossOrigin(origins="http://localhost:8080")
	@PutMapping(path = "/sales/transactions")
	public void updateTransaction(@RequestBody Transaction t) {
		restTemplate.put("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions", t, String.class);
	}

	// create a transaction
	@CrossOrigin(origins="http://localhost:8080")
	@PostMapping(path = "/sales/transactions")
	public ResponseEntity<String> createTransaction(@RequestBody Transaction t) {
		return restTemplate.postForEntity("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions", t,
				String.class);
	}

	// read a transaction by id
	@CrossOrigin(origins="http://localhost:8080")
	@DeleteMapping(path = "/sales/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		restTemplate.delete("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions/" + id,
				String.class);
	}
	
	// ------------ Requests for Account --------------------------------
	
	@GetMapping(path = "/accountant")
	@CrossOrigin(origins="http://localhost:8080")
	public ResponseEntity<String> findAllManagers() {
		return restTemplate.getForEntity("http://localhost:" + ACCOUNT_SERVICE + "/accountant",
				String.class);
	}
	
	@GetMapping(path = "/accountant/reports/sales")
	@CrossOrigin(origins="http://localhost:8080")
	public ResponseEntity<String> getSalesReport() {
		return restTemplate.getForEntity("http://localhost:" + ACCOUNT_SERVICE + "/accountant/reports/sales",
				String.class);
	}
	
	@GetMapping(path = "/accountant/reports/taxes")
	@CrossOrigin(origins="http://localhost:8080")
	public ResponseEntity<String> getTaxReport() {
		return restTemplate.getForEntity("http://localhost:" + ACCOUNT_SERVICE + "/accountant/reports/taxes",
				String.class);
	}

}
