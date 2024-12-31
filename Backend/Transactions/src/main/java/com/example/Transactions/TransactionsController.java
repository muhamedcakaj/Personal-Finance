package com.example.Transactions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionsController {

    private TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService){
        this.transactionsService=transactionsService;
    }

    @GetMapping(path = "/v1/transactions/{userUsername}")
    public List<TransactionsEntity> getTransactionsByUsername(@PathVariable(name = "userUsername") String userUsername) {
        return transactionsService.findByuserUsernameIgnoreCase(userUsername);
    }

    @GetMapping("/v1/transactions/category/{userUsername}")
    public List<Map<String, Object>> getCategorySums(@PathVariable String userUsername) {
        return transactionsService.calculateCategorySums(userUsername);
    }
    @PostMapping(path = "/v1/transactions")
    public ResponseEntity<?> createUser(@RequestBody TransactionsEntity transactions) {
        System.out.println(transactions.getUserUsername());
        try {
            TransactionsEntity createdUser = transactionsService.save(transactions);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping(path="/v1/transactions/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "username") String username) {
        try {
            transactionsService.deleteByUsername(username);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transactions not found with username: " + username);
        }
    }
    @DeleteMapping(path="/v1/transactions/oneTransaction/{id}")
    public ResponseEntity<?> deleteUserr(@PathVariable(name = "id") int id) {
        try {
            transactionsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found with id: " + id);
        }
    }
    //PutMapping for only one transaction
    @PutMapping("/v1/transactions/{id}")
    public ResponseEntity<String> updateUserColumns(
            @PathVariable int id,
            @RequestBody Map<String, String> columnUpdates) {

        try {
            String updatedUser = transactionsService.updateTransactionColums(id, columnUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/v1/transactions/userUsername/{userUsername}")
    public ResponseEntity<String> updateTransactionUsername(
            @PathVariable String userUsername,
            @RequestBody Map<String, String> columnUpdates) {

        try {
            String updatedUser = transactionsService.updateTransactionUsername(userUsername, columnUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Methods for Admin Dashboard

    @GetMapping("/v1/transactions/monthlyCounts")
    public List<Integer> getMonthlyTransactionCounts() {
        return transactionsService.getMonthlyTransactionCounts();
    }
    @GetMapping("/v1/transactions/findUserWhoMakesMostTransactions")
    public List<Object[]> findUserWhoMadeMostTransactions(){
        return this.transactionsService.findUserWhoMadeMostTransactions();
    }
    @GetMapping("/v1/transactions/findUserWhoMadeFewestTransactions")
    public List<Object[]>findUserWhoMadeFewestTransactions(){
        return this.transactionsService.findUserWhoMadeFewestTransactions();
    }
    @GetMapping("/v1/transactions/theMostUsedCategoryInTransactions")
    public List<Object[]>theMostUsedCategoryInTransactions(){
        return this.transactionsService.theMostUsedCategoryInTransactions();
    }
    @GetMapping("/v1/transactions/totalNumberOfTransactions")
    public List<Object[]> totalNumberOfTransactions(){
        return this.transactionsService.totalNumberOfTransactions();
    }
    @GetMapping("/v1/transactions/AllTransactions")
    public List<TransactionsEntity>allTransactions(){
        return this.transactionsService.listAllTransactions();
    }
}
