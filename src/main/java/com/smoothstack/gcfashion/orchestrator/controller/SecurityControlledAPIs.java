package com.smoothstack.gcfashion.orchestrator.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
//import com.smoothstack.gcfashion.orchestrator.db.UserDAO;
import com.smoothstack.gcfashion.orchestrator.entity.User;

@RestController
@RequestMapping("/gcfashions")
public class SecurityControlledAPIs {
	// private UserDAO userDao;

	@Autowired
	RestTemplate restTemplate;

//	public SecurityControlledAPIs(UserDAO userDao) {
//		this.userDao = userDao;
//	}

	public static final int ONLINE_SERVICE = 8081;
	public static final int SALES_SERVICE = 8082;

	// ------------Requests for Online Stores-----------------------
		// read all products
		@GetMapping(path = "/shop/products")
		public ResponseEntity<String> allProducts() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/products", String.class);
		}

		// read by products by Id
		@GetMapping("/shop/products/{productId}")
		public ResponseEntity<String> allProductsByIdOnline(@PathVariable Long productId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/products/" + productId,
					String.class);
		}
		
		// read all coupons
		@GetMapping(path = "/shop/coupons")
		public ResponseEntity<String> allCouponsOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/coupons", String.class);
		}
		
		// read by transaction by Id
		@GetMapping("/shop/transactions/{userId}")
		public ResponseEntity<String> allTransactionsByIdOnline(@PathVariable Long userId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions/" + userId,
					String.class);
		}

		 //update a transaction
		@PostMapping("/shop/transactions/{userId}")
		public void updateTransactionsByIdOnline(@RequestBody Transaction transaction, @PathVariable Long userId) throws SQLException {
			restTemplate.postForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions/" + userId, transaction,
					String.class);
		}
		
		//create a transaction
		@PutMapping("/shop/transactions")
		public void createTransactionsByIdOnline(@RequestBody Transaction transaction) throws SQLException {
			restTemplate.put("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions", transaction,
					String.class);
		}
		
		@DeleteMapping("/shop/transactions/{userId}")
		public void delTransactionsByIdOnline(@RequestBody Transaction transaction, @PathVariable Long userId) throws SQLException {
			restTemplate.delete("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/transactions/" + userId, transaction,
					String.class);
		}
		
		// read all categories
		@GetMapping(path = "/shop/categories")
		public ResponseEntity<String> allCategoriesOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/categories", String.class);
		}
		
		// read by products by category Id
		@GetMapping("/shop/categories/{categoryId}/products")
		public ResponseEntity<String> CategoriesByIdOnline(@PathVariable Long categoryId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/categories/" + categoryId +"/products",
					String.class);
		}
		
		// read all subcategories
		@GetMapping(path = "/shop/subcategories")
		public ResponseEntity<String> allSubcategoriesOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/subcategories", String.class);
		}
		
		// read all stores
		@GetMapping(path = "/shop/stores")
		public ResponseEntity<String> allStoresOnline() throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/stores", String.class);
		}
		
		// read by users by user Id
		@GetMapping("/shop/account/users/{userId}")
		public ResponseEntity<String> userByIdOnline(@PathVariable Long userId) throws SQLException {
			return restTemplate.getForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/account/users/" + userId,
					String.class);
		}
		
		@PostMapping("shop/account/users/{userId}")
		public void updateUserByIdOnline(@RequestBody User user, @PathVariable Long userId) throws SQLException {
			restTemplate.postForEntity("http://localhost:" + ONLINE_SERVICE + "/gcfashions/shop/account/users/" + userId, user,
					String.class);
		}
		
	// ------------ Requests for Sales --------------------------------


	// read a transaction by id
	@GetMapping(path = "/sales/transactions/{id}")
	public ResponseEntity<String> getTransactionById(@PathVariable Long id) {
		return restTemplate.getForEntity("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions/" + id,
				String.class);
	}

	// update a transaction
	@PutMapping(path = "/sales/transactions")
	public void updateTransaction(@RequestBody Transaction t) {
		restTemplate.put("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions", t, String.class);
	}

	// create a transaction
	@PostMapping(path = "/sales/transactions")
	public ResponseEntity<String> createTransaction(@RequestBody Transaction t) {
		return restTemplate.postForEntity("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions", t,
				String.class);
	}

	// read a transaction by id
	@DeleteMapping(path = "/sales/transactions")
	public void deleteTransaction(@PathVariable Long id) {
		restTemplate.delete("http://localhost:" + SALES_SERVICE + "/gcfashions/sales/transactions/" + id,
				String.class);
	}

}
