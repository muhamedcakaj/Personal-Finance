package Bilanci.Bilanci;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BilanciServicesImpl implements BilanciServices {

    private BilanciRepository bilanciRepository;

    public BilanciServicesImpl(BilanciRepository bilanciRepository){
        this.bilanciRepository=bilanciRepository;
    }
    @Override
    public BilanciEntity findById(int id) {
        Optional<BilanciEntity> optionalUser = bilanciRepository.findById(id);
        return optionalUser.orElseThrow(() -> new RuntimeException("Bilanci not found with ID: " + id));
    }

    @Override
    @Cacheable(value = "bilanc", key = "#bilanciEntity.username")
    public BilanciEntity save(BilanciEntity bilanciEntity) {
        try {
            return bilanciRepository.save(bilanciEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Bilanc already exists. Please try another one.");
        }
    }


    @CacheEvict(value = "bilanc", key = "#username")
    @CachePut(value = "bilanc", key = "#username")
    @Override
    public String updateBilancColumns(String username, Map<String, String> columnUpdates) {
        BilanciEntity bilanc = bilanciRepository.findByUsernameIgnoreCase(username);

        for (Map.Entry<String, String> entry : columnUpdates.entrySet()) {
            String columnName = entry.getKey();
            String columnValue = entry.getValue();
            switch (columnName) {
                case "username":
                    bilanc.setUsername(columnValue);
                    break;
                case "bilanci":
                    String[]array=columnValue.split(" ");
                    if(columnValue.startsWith("+")){
                        float bilanci=bilanc.getBilanci()+Float.parseFloat(array[1]);
                        bilanc.setBilanci(bilanci);
                    }else{
                        float bilanci=bilanc.getBilanci()-Float.parseFloat(array[1]);
                        bilanc.setBilanci(bilanci);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid column name: " + columnName);
            }
        }

        BilanciEntity updatedBilanc = bilanciRepository.save(bilanc);
        return updatedBilanc.toString();
    }



    @Override
    public void deleteByUsername(String userName){
       this.bilanciRepository.deleteByUsername(userName);
    }


    @Cacheable(value = "bilanc", key = "#username")
    @Override
    public BilanciEntity findByUsernameIgnoreCase(String username) {
        return bilanciRepository.findByUsernameIgnoreCase(username);
    }

    //Deleting the balance from chachable method
    @CacheEvict(value = "bilanc", key = "#username")
    public void deleteCacheEvict(String username){

    }

    @Override
    public List<Object[]> findTopBilanci() {
        return bilanciRepository.findTopBilanci();
    }
    @Override
    public List<Object[]> findLowestBilanci() {
        return bilanciRepository.findLowestBilanci();
    }
}
