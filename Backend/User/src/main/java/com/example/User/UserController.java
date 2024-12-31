package com.example.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.mail.*;
import jakarta.mail.internet.*;
@RestController
public class UserController {
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService,JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping(path = "/v1/user/id/{id}")
    public UserEntity getById(@PathVariable(name="id")int id) {
        return this.userService.findById(id);
    }

    @GetMapping(path="/v1/user/username/{userName}")
    public UserEntity getByUseraname(@PathVariable(name="userName")String userName){
        return this.userService.findByUserNameIgnoreCase(userName);
    }

    @PostMapping(path="/v1/user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            UserEntity createdUser = userService.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/v1/user/{userName}")
    public ResponseEntity<Object> updateUserColumns(
            @PathVariable(name="userName") String userName,
            @RequestBody Map<String, String> columnUpdates) {
        try {
            String updatedUser = userService.updateBilancColumns(userName, columnUpdates);
            for (Map.Entry<String, String> entry : columnUpdates.entrySet()) {
                String columnName = entry.getKey();
                String columnValue = entry.getValue();
                // Update the specified column
                if(columnName.equals("userName")) {
                    String token = this.jwtUtil.generateToken(columnValue,"User");

                    // Return the token in the response
                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.ok("DONE!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(path = "/v1/user/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("userName");
        String password = loginRequest.get("password");

        // Check if the username exists
        UserEntity user = userService.findByUserNameIgnoreCase(username);
        if (user == null) {
            userService.deleteCacheEvict(username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username doesn't exist");
        }

        // Validate the password
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect");
        }

        // If authentication succeeds
        return ResponseEntity.ok("Login successful");
    }

    //When logging out delete existing userName in chaching
    @DeleteMapping("/v1/user/deleteChaching/{userName}")
    public void String(@PathVariable(name="userName")String userName){
        if(userService.findByUserNameIgnoreCase(userName)!=null){
            userService.deleteCacheEvict(userName);
        }else{
            System.out.println("User doesn't exists");
        }
    }

    //admin Dashboard
    @GetMapping("/v1/user/totalUserNumber")
    public List<Object[]> totalUserNumber(){
        return this.userService.totalUserNumber();
    }

//                                 --Security Methods DOWN HERE--


    //Validating the Code
    @PostMapping("/v1/user/validateCode")
    public ResponseEntity<Object> validateMfaCode(@RequestBody Map<String, String> mfaRequest) {
        String userName = mfaRequest.get("userName");
        String submittedCode = mfaRequest.get("mfaCode");

        UserEntity user = userService.findByUserNameIgnoreCase(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (user.getMfaCode() != 0 && user.getMfaCode()==(Integer.parseInt(submittedCode)) &&
                user.getMfaCodeExpiration().isAfter(LocalDateTime.now())) {
            // MFA validated, clear the code to prevent reuse
            user.setMfaCode(0);
            user.setMfaCodeExpiration(null);
            userService.save(user);

            //Generating the Token
            String token = this.jwtUtil.generateToken(user.getUserName(), user.getRole());
            System.out.println(token);
            // Return the token in the response
            Map<String, String> response = new HashMap<>();
            response.put("message", "MFA validation successful");
            response.put("token", token);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired MFA code");
    }

    //Sending the code to email of the USER
    @PostMapping("/v1/user/mfaCode/{userName}")
    public ResponseEntity<String> generateMfaCode(@PathVariable(name="userName") String userName) throws MessagingException {
        UserEntity user = userService.findByUserNameIgnoreCase(userName);

        // Generate a random 6-digit MFA code
        String mfaCode = String.format("%06d", new Random().nextInt(1000000));
        user.setMfaCode(Integer.parseInt(mfaCode));
        user.setMfaCodeExpiration(LocalDateTime.now().plusMinutes(10)); // Code expires in 10 minutes
        userService.save(user);

        // Send the code via email (mocked here)
        sendMfaCode(user.getEmail(),mfaCode);

        return ResponseEntity.ok("MFA code sent");
    }


    //Generating the code
    public void sendMfaCode(String userEmail, String code) throws MessagingException {
        String host = "smtp.gmail.com";
        String from = "muhamedcakaj57@gmail.com";
        String password = "medo xysf nzeq dfow";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("Your MFA Code");
        message.setText("Your MFA code is: " + code);

        Transport.send(message);
    }
}
