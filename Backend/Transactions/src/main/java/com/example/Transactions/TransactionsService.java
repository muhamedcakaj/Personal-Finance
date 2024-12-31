package com.example.Transactions;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TransactionsService {

    public TransactionsEntity findById(int id);
    public TransactionsEntity save(TransactionsEntity transactionsEntity);
    public List<TransactionsEntity> listAllTransactions();
    public String updateTransactionColums(int id, Map<String, String> columnUpdates);
    public void deleteByUsername(String username);
    public void deleteById(int id);

    public List<TransactionsEntity> findByuserUsernameIgnoreCase(String userUsername);

    List<Map<String, Object>> calculateCategorySums(String userUsername);

    public String updateTransactionUsername(String userUsername,Map<String, String> columnUpdates);
    public List<Integer> getMonthlyTransactionCounts();

    public List<Object[]> findUserWhoMadeMostTransactions();

    public List<Object[]> findUserWhoMadeFewestTransactions();
    public List<Object[]> theMostUsedCategoryInTransactions();
    public List<Object[]> totalNumberOfTransactions();

}
