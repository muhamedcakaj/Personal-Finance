package com.example.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface TransactionsRepository extends JpaRepository<TransactionsEntity,Integer>{
    List<TransactionsEntity> findByuserUsernameIgnoreCase(@PathVariable("userUsername")String userUsername);


    @Query("SELECT t.category AS category, SUM(t.sum) AS totalSum " +
            "FROM TransactionsEntity t " +
            "WHERE LOWER(t.userUsername) = LOWER(:userUsername) " +
            "GROUP BY t.category")
    List<Map<String, Object>> calculateCategorySums(@Param("userUsername") String userUsername);

    @Query(value = "SELECT COUNT(*) as 'Count' "+
           "FROM transactions "+
           "WHERE DATEPART(yyyy, transaction_date) = 2024 "+
    "GROUP BY DATEPART(mm, transaction_date) ",
            nativeQuery = true)
    List<Integer> getMonthlyTransactionCounts();

    @Query(value = "Select TOP 1 MAX(t.userUsername) as userName,COUNT(t.userUsername) as Number " +
            "from Transactions t " +
            "where t.userUsername = (Select MAX(userUsername) " +
            "                        from transactions) " +
            "group by t.userUsername ",
             nativeQuery = true)
    List<Object[]> findUserWhoMadeMostTransactions();

    @Query(value = "Select TOP 1 MIN(t.userUsername) as userName,COUNT(t.userUsername) as Number "+
            "from Transactions t "+
            "where t.userUsername = (Select MIN(userUsername) "+
            "                        from transactions) "+
            "group by t.userUsername ",
            nativeQuery = true)
    List<Object[]> findUserWhoMadeFewestTransactions();

    @Query(value = "Select TOP 1 t.category,COUNT(*) as number " +
            "from transactions t " +
            "group by t.category " +
            "having COUNT(*) >=(Select TOP 1 Count(t.category) " +
            "                 from transactions t " +
            "                 group by t.category) ",
                               nativeQuery = true)
    List<Object[]> theMostUsedCategoryInTransactions();

@Query(value = "Select count(*) as totalNumberOfTransactions " +
        "from transactions ",
         nativeQuery = true)
  List<Object[]> totalNumberOfTransactions();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Transactions WHERE LOWER(userUsername) = LOWER(:username)", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);
}
