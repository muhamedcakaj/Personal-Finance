package Bilanci.Bilanci;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
public class BilanciController {

    private BilanciServices bilanciServices;

    @Autowired
    public BilanciController(BilanciServices bilanciServices){
        this.bilanciServices=bilanciServices;
    }

    @GetMapping(path = "/v1/bilanc/{id}")
    public BilanciEntity getById(@PathVariable(name="id")int id){
        return this.bilanciServices.findById(id);
    }

    @GetMapping(path = "/v1/bilanc/username/{username}")
    public BilanciEntity getByUsername(@PathVariable(name="username")String username){
        return this.bilanciServices.findByUsernameIgnoreCase(username);
    }

    @PostMapping(path = "/v1/bilanc")
    public ResponseEntity<?> createUser(@RequestBody BilanciEntity user) {
        try {
            BilanciEntity createdUser = bilanciServices.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(path="/v1/bilanc/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userName") String userName) {
        try {
            bilanciServices.deleteByUsername(userName);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bilanc not found with username: " + userName);
        }
    }
    @PutMapping("/v1/bilanc/{username}")
    public ResponseEntity<String> updateUserColumns(
            @PathVariable String username,
            @RequestBody Map<String, String> columnUpdates) {

        try {
            String updatedUser = bilanciServices.updateBilancColumns(username, columnUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //When logging out delete existing bilanc in chaching
    @DeleteMapping("/v1/bilanc/deleteChaching/{username}")
    public void String(@PathVariable(name="username")String username){
        if(bilanciServices.findByUsernameIgnoreCase(username)!=null){
            bilanciServices.deleteCacheEvict(username);
        }else{
            System.out.println("User doesn't exists");
        }
    }

    @GetMapping("/v1/bilanc/top")
    public List<Object[]> getTopBilanci() {
        return bilanciServices.findTopBilanci();
    }

    @GetMapping("/v1/bilanc/lowest")
    public List<Object[]> getLowestBilanci() {
        return bilanciServices.findLowestBilanci();
    }
}
