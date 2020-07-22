package com.smoothstack.gcfashion.orchestrator.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.smoothstack.gcfashion.orchestrator.db.DbInit;
import com.smoothstack.gcfashion.orchestrator.db.UserDAO;
import com.smoothstack.gcfashion.orchestrator.entity.User;

@RestController
@RequestMapping("/gcfashions")
public class SecurityControlledAPIs {
	private UserDAO userDao;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DbInit dbInit;

	public SecurityControlledAPIs(UserDAO userDao) {
		this.userDao = userDao;
	}

	public static final String ONLINE_SERVICE = "http://ec2-18-191-27-130.us-east-2.compute.amazonaws.com:8081";
	public static final String SALES_SERVICE = "http://ec2-18-219-254-63.us-east-2.compute.amazonaws.com:8082";
	public static final String ACCOUNT_SERVICE = "http://ec2-18-219-109-245.us-east-2.compute.amazonaws.com:8083";
	public static final String ANGULAR_APP = "http://gcfashion.s3-website-us-east-1.amazonaws.com:8080";

	// ------------Requests for Online Stores-----------------------
	// read all products
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/products")
	public ResponseEntity<String> allProducts() throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/products", String.class);
	}

	// read products by Id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/shop/products/{productId}")
	public ResponseEntity<String> allProductsByIdOnline(@PathVariable Long productId) throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/products/" + productId, String.class);
	}

	// search products by string
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/shop/products/like/{productName}")
	public ResponseEntity<String> searchProducts(@PathVariable String productName) {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/products/like/" + productName,
				String.class);
	}

	// read all coupons
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/coupons")
	public ResponseEntity<String> allCouponsOnline() throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/coupons", String.class);
	}

	// read by transaction by Id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/account/users/{userId}/transactions")
	public ResponseEntity<String> allTransactionsByIdOnline(@PathVariable Long userId) throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/account/users/" + userId + "/transactions/",
				String.class);
	}

	// read by transaction by Id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/account/users/{userId}/role")
	public ResponseEntity<String> roleByIdOnline(@PathVariable Long userId) throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/account/users/" + userId + "/role/",
				String.class);
	}

	// update a transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping("/account/users/{userId}/transactions")
	public void updateTransactionsByIdOnline(@RequestBody Transaction transaction, @PathVariable Long userId)
			throws SQLException {
		restTemplate.postForEntity(ONLINE_SERVICE + "/gcfashions/account/users/" + userId + "/transactions/",
				transaction, String.class);
	}

	// create a transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PutMapping("/account/users/{userId}/transactions")
	public void createTransactionsByIdOnline(@RequestBody Transaction transaction, @PathVariable Long userId)
			throws SQLException {
		restTemplate.put(ONLINE_SERVICE + "/gcfashions/account/users/" + userId + "/transactions/", transaction,
				String.class);
	}

	// read all categories
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/categories")
	public ResponseEntity<String> allCategoriesOnline() throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/categories", String.class);
	}

	// read by products by category Id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/shop/categories/{categoryId}/products")
	public ResponseEntity<String> CategoriesByIdOnline(@PathVariable Long categoryId) throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/categories/" + categoryId + "/products",
				String.class);
	}

	// read all subcategories
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/categories/{categoryId}/subcategories/{subcategoryId}")
	public ResponseEntity<String> subcategoriesOnline(@PathVariable Long categoryId, @PathVariable Long subcategoryId)
			throws SQLException {
		return restTemplate.getForEntity(
				ONLINE_SERVICE + "/gcfashions/shop/categories/" + categoryId + "/subcategories/" + subcategoryId,
				String.class);
	}

	// read all stores
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/stores")
	public ResponseEntity<String> allStoresOnline() throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/stores", String.class);
	}

	// read by users by user Id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping("/account/transactions/users/{userId}")
	public ResponseEntity<String> userByIdOnline(@PathVariable Long userId) throws SQLException {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/account/users/" + userId + "/transactions",
				String.class);
	}

	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping("/account/users/{userId}")
	public void updateUserByIdOnline(@RequestBody User user, @PathVariable Long userId) throws SQLException {
		restTemplate.postForEntity(ONLINE_SERVICE + "/gcfashions/account/users/" + userId, user, String.class);
	}

//	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping("/user")
	public void createUserByIdOnline(@RequestBody User user) throws SQLException {
		dbInit.saveUser(user);
	}

	// ------------ Requests for Sales --------------------------------

	// get all transactions with open status
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/sales/transactions/complete")
	public ResponseEntity<String> getAllCompleteTransactions() {
		return restTemplate.getForEntity(SALES_SERVICE + "/gcfashions/sales/transactions/complete", String.class);
	}

	// get all transactions with open status
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/sales/transactions/complete/like/{transactionId}")
	public ResponseEntity<String> getAllOpenTransactionsLike(@PathVariable String transactionId) {

		Long t;

		try {
			t = Long.valueOf(transactionId);
			return restTemplate.getForEntity(SALES_SERVICE + "/gcfashions/sales/transactions/complete/like/" + t,
					String.class);
		} catch (Exception e) {
			System.out.println("No Long value");
			return ResponseEntity.noContent().build();
		}

	}

	// get all transactions with open status
	@CrossOrigin(origins = ANGULAR_APP)
	@PutMapping(path = "sales/transactions/refund")
	public void refund(@RequestBody Map<String, Object> values) {
		restTemplate.put(SALES_SERVICE + "/gcfashions/sales/transactions/refund", values, String.class);
	}

	// ------------ Requests for Shop --------------------------------

	// read a transaction by id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/sales/users/{id}")
	public ResponseEntity<String> getUserById(@PathVariable Long id) {
		return restTemplate.getForEntity(SALES_SERVICE + "/gcfashions/sales/users/" + id, String.class);
	}

	// read a inventory by id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/sales/inventory/{id}")
	public ResponseEntity<String> getInventoryById(@PathVariable Long id) {
		return restTemplate.getForEntity(SALES_SERVICE + "/gcfashions/sales/inventory/" + id, String.class);
	}

	// read a product by id
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/sales/products/{id}")
	public ResponseEntity<String> getProductById(@PathVariable Long id) {
		return restTemplate.getForEntity(SALES_SERVICE + "/gcfashions/sales/products/" + id, String.class);
	}

	// read open transactions for a userId
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/transactions/open/userid/{userId}")
	public ResponseEntity<String> getOpenTransactionByUserId(@PathVariable Long userId) {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/transactions/open/userid/" + userId,
				String.class);
	}

	// read list of items in cart by userId
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/transactions/cart/userid/{userId}")
	public ResponseEntity<String> getCartByUserId(@PathVariable Long userId) {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/transactions/cart/userid/" + userId,
				String.class);
	}

	// read coupon associated with open transaction for a userId
	@CrossOrigin(origins = ANGULAR_APP)
	@GetMapping(path = "/shop/transactions/open/coupon/userid/{userId}")
	public ResponseEntity<String> getCouponByUserId(@PathVariable Long userId) {
		return restTemplate.getForEntity(ONLINE_SERVICE + "/gcfashions/shop/transactions/open/coupon/userid/" + userId,
				String.class);
	}

	// update a transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PutMapping(path = "/shop/transactions")
	public void updateTransaction(@RequestBody Transaction t) {
		restTemplate.put(ONLINE_SERVICE + "/gcfashions/shop/transactions", t, String.class);
	}

	// update a transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PutMapping(path = "/shop/checkout")
	public void updateTransactionCost(@RequestBody Map<String, Object> values) {
		restTemplate.put(ONLINE_SERVICE + "/gcfashions/shop/checkout", values, String.class);
	}

	// create a transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping(path = "/shop/transactions")
	public ResponseEntity<String> createTransaction(@RequestBody Transaction t) {
		return restTemplate.postForEntity(ONLINE_SERVICE + "/gcfashions/shop/transactions", t, String.class);
	}

	// update the coupon associated with a user's open transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping(path = "shop/transactions/open/coupon")
	public ResponseEntity<String> addCouponByUserId(@RequestBody Transaction t) {
		try {
			return restTemplate.postForEntity(ONLINE_SERVICE + "/gcfashions/shop/transactions/open/coupon", t,
					String.class);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	// Creates a new stripe payment intent and returns the client_secret to complete
	// the transaction
	@CrossOrigin(origins = ANGULAR_APP)
	@PostMapping(path = "/shop/checkout")
	public ResponseEntity<String> createPaymentIntent(@RequestBody Transaction t) {
		return restTemplate.postForEntity(ONLINE_SERVICE + "/gcfashions/shop/checkout", t, String.class);
	}

	// read a transaction by id
	@CrossOrigin(origins = ANGULAR_APP)
	@DeleteMapping(path = "/shop/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		restTemplate.delete(ONLINE_SERVICE + "/gcfashions/shop/transactions/" + id, String.class);
	}

	@CrossOrigin(origins = ANGULAR_APP)
	@DeleteMapping(path = "/shop/transactions/open/userid/{userId}/sku/{sku}")
	public void removeFromOpenTransactionByUserId(@PathVariable Long userId, @PathVariable Long sku) {
		restTemplate.delete(ONLINE_SERVICE + "/gcfashions/shop/transactions/open/userid/" + userId + "/sku/" + sku,
				String.class);
	}

	// ------------ Requests for Account --------------------------------

	@GetMapping(path = "/accountant")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> findAllManagers() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant", String.class);
	}

	@GetMapping(path = "/accountant/reports/sales")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> getSalesReport() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/sales", String.class);
	}

	@GetMapping(path = "/accountant/reports/taxes")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> getTaxReport() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/taxes", String.class);
	}

	@GetMapping(path = "/accountant/reports/salesbycat")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> salesbycat() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/salesbycat", String.class);
	}

	@GetMapping(path = "/accountant/reports/totalsales")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> totalsales() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/totalsales", String.class);
	}

	@GetMapping(path = "/accountant/reports/totaltaxes")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> totaltaxes() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/totaltaxes", String.class);
	}

	@GetMapping(path = "/accountant/reports/salesperday")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> salesperday() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/salesperday", String.class);
	}

	@GetMapping(path = "/accountant/reports/salespertrans")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> salespertrans() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/salespertrans",
				String.class);
	}

	@GetMapping(path = "/accountant/reports/volperloc")
	@CrossOrigin(origins = ANGULAR_APP)
	public ResponseEntity<String> volperloc() {
		return restTemplate.getForEntity(ACCOUNT_SERVICE + "/gcfashions/accountant/reports/volperloc", String.class);
	}

}
