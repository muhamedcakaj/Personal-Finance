package Bilanci.Bilanci;

import java.util.List;
import java.util.Map;

public interface BilanciServices {
public BilanciEntity findById(int id);
public BilanciEntity save(BilanciEntity bilanciEntity);

public String updateBilancColumns(String username, Map<String, String> columnUpdates);
public void deleteByUsername(String userName);

public BilanciEntity findByUsernameIgnoreCase(String username);

public void deleteCacheEvict(String username);

public List<Object[]> findTopBilanci();

public List<Object[]> findLowestBilanci();
}
