package Bilanci.Bilanci;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BilanciRepository extends JpaRepository<BilanciEntity,Integer> {

    BilanciEntity findByUsernameIgnoreCase(String userName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Bilanci WHERE LOWER(username) = LOWER(:username)", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);
    @Query(value = "SELECT TOP 1 b.username, b.bilanci FROM Bilanci b WHERE b.bilanci >= (SELECT MAX(b.bilanci) FROM Bilanci b)", nativeQuery = true)
    List<Object[]> findTopBilanci();

    @Query(value = "SELECT TOP 1 b.username, b.bilanci FROM Bilanci b WHERE b.bilanci <= (SELECT MIN(b.bilanci) FROM Bilanci b)", nativeQuery = true)
    List<Object[]> findLowestBilanci();

}
