package com.example.Transactions;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository){
        this.transactionsRepository=transactionsRepository;
    }


    public TransactionsEntity findById(int id){
        Optional<TransactionsEntity> optionalUser = transactionsRepository.findById(id);
        return optionalUser.orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }
    @Override
    public TransactionsEntity save(TransactionsEntity transactionsEntity) {
        try {
            return transactionsRepository.save(transactionsEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Transactions already exists. Please try another one.");
        }
    }
    public List<TransactionsEntity> listAllTransactions(){
        return this.transactionsRepository.findAll();
    }
    @Override
    public String updateTransactionColums(int id, Map<String, String> columnUpdates){
        TransactionsEntity transactions = findById(id);

        if(transactions==null){
            return null;
        }

        for (Map.Entry<String, String> entry : columnUpdates.entrySet()) {
            String columnName = entry.getKey();
            String columnValue = entry.getValue();
            switch (columnName) {
                case "purpose":
                    transactions.setPrupose(columnValue);
                        break;
                case"category":
                    transactions.setCategory(columnValue);
                    break;
                case"sum":
                    transactions.setSum(Float.parseFloat(columnValue));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid column name: " + columnName);
            }
        }

        TransactionsEntity updateTransactions = transactionsRepository.save(transactions);
        return updateTransactions.toString();
    }

    @Override
    public void deleteByUsername(String username){
        this.transactionsRepository.deleteByUsername(username);
    }
    public void deleteById(int id){
        this.transactionsRepository.deleteById(id);
    }

    @Override
    public List<TransactionsEntity> findByuserUsernameIgnoreCase(String userUsername) {
        return this.transactionsRepository.findByuserUsernameIgnoreCase(userUsername);
    }

    @Override
    public List<Map<String, Object>> calculateCategorySums(String userUsername) {
        return transactionsRepository.calculateCategorySums(userUsername);
    }

    @Override
    public String updateTransactionUsername(String userUsername, Map<String, String> columnUpdates) {
        String updateduserUsername=null;
        for (Map.Entry<String, String> entry : columnUpdates.entrySet()) {
            updateduserUsername=entry.getValue();
        }
        List<TransactionsEntity>usersTransactions=findByuserUsernameIgnoreCase(userUsername);
        if(usersTransactions != null){

            String finalUpdateduserUsername = updateduserUsername;
            usersTransactions.parallelStream()
                .forEach(transaction -> {
                    // Process each transaction
                    transaction.setUserUsername(finalUpdateduserUsername);
                    transactionsRepository.save(transaction);
                });
        return "Transaction changed succesfully";
            }
        return null;
    }



    public List<Integer> getMonthlyTransactionCounts() {
        return transactionsRepository.getMonthlyTransactionCounts();
    }

    @Override
    public List<Object[]> findUserWhoMadeMostTransactions() {
        return this.transactionsRepository.findUserWhoMadeMostTransactions();
    }
    public List<Object[]> findUserWhoMadeFewestTransactions(){
        return this.transactionsRepository.findUserWhoMadeFewestTransactions();
    }
    public List<Object[]> theMostUsedCategoryInTransactions(){
        return this.transactionsRepository.theMostUsedCategoryInTransactions();
    }
    public List<Object[]> totalNumberOfTransactions(){
        return this.transactionsRepository.totalNumberOfTransactions();
    }

}
