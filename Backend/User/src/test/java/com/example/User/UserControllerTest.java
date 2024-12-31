package com.example.User;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private UserController userController;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserById() throws Exception {
        // Create a mock user
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);
        mockUser.setName("Muhamed");
        mockUser.setSurname("Cakaj");
        mockUser.setUserName("meticakaj");
        mockUser.setEmail("muhamedcakaj57@gmail.com");
        mockUser.setPassword("Muhamed10");
        mockUser.setRole("User");

        // Simulate the service method to return the mock user
        when(userService.findById(1)).thenReturn(mockUser);

        // Perform the request and check the response status
        mockMvc.perform(get("/v1/user/id/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void getByUseraname()throws Exception{
        UserEntity mockUser=new UserEntity();
        mockUser.setId(1);
        mockUser.setName("Muhamed");
        mockUser.setSurname("Cakaj");
        mockUser.setUserName("meticakaj");
        mockUser.setEmail("muhamedcakaj57@gmail.com");
        mockUser.setPassword("Muhamed10");
        mockUser.setRole("User");

        when(userService.findByUserNameIgnoreCase("meticakaj")).thenReturn(mockUser);

        mockMvc.perform(get("/v1/user/username/meticakaj"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a mock UserEntity object
        UserEntity userToCreate = new UserEntity();
        userToCreate.setId(1042);
        userToCreate.setName("Afrim");
        userToCreate.setSurname("Cakaj");
        userToCreate.setUserName("afrimcakaj");
        userToCreate.setEmail("muhametcakaj57@gmail.com");
        userToCreate.setPassword("Afrim10");
        userToCreate.setRole("USER");

        when(userService.save(any(UserEntity.class))).thenReturn(userToCreate);

        mockMvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userToCreate)))  // Convert userToCreate to JSON
                .andExpect(status().isCreated())  // Expect HTTP 201 status
                .andExpect(jsonPath("$.id").value(1042))  // Verify the ID in the response
                .andExpect(jsonPath("$.name").value("Afrim"))
                .andExpect(jsonPath("$.surname").value("Cakaj"))
                .andExpect(jsonPath("$.userName").value("afrimcakaj"))
                .andExpect(jsonPath("$.email").value("muhametcakaj57@gmail.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    public void updateUserColumns()throws Exception{
        UserEntity userToCreate = new UserEntity();
        userToCreate.setId(1042);
        userToCreate.setName("Afrim");
        userToCreate.setSurname("Cakaj");
        userToCreate.setUserName("afrimcakaj");
        userToCreate.setEmail("muhametcakaj57@gmail.com");
        userToCreate.setPassword("Afrim10");
        userToCreate.setRole("USER");

        userService.save(userToCreate);

        Map<String, String> myMap = new HashMap<>();
        myMap.put("name","Unknow");
        myMap.put("surname","User");
        when(userService.updateBilancColumns(userToCreate.getUserName(),myMap)).thenReturn("ok");

        // Perform the PUT request and validate the response
        // Perform the PUT request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/{userName}", userToCreate.getUserName())  // Use the actual username
                        .contentType(MediaType.APPLICATION_JSON)  // Set content type
                        .content(new ObjectMapper().writeValueAsString(myMap)))  // Convert map to JSON string for request body
                .andExpect(MockMvcResultMatchers.status().isOk());  // Expect status OK (200)
    }

    @Test
    public void loginUser()throws Exception{
        UserEntity user=new UserEntity();
        user.setId(1);
        user.setName("John");
        user.setSurname("Bravo");
        user.setUserName("johny_");
        user.setPassword("Johny10");
        user.setEmail("johnybravo57@gmail.com");
        user.setRole("User");

        userService.save(user);

        Map<String, String> myMap = new HashMap<>();
        myMap.put("userName","johny_");
        myMap.put("password","Johny10");

        when(userService.findByUserNameIgnoreCase("johny_")).thenReturn(user);

        mockMvc.perform(post("/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(myMap)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

    }

    @Test
    public void validateMfaCode() throws Exception {
        // Mock user entity
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setName("John");
        user.setSurname("Bravo");
        user.setUserName("johny_");
        user.setPassword("Johny10");
        user.setEmail("johnybravo57@gmail.com");
        user.setRole("User");
        user.setMfaCode(222365064);
        user.setMfaCodeExpiration(LocalDateTime.now().plusMinutes(10)); // Future expiration time

        // Mock the behavior of userService.findByUserNameIgnoreCase
        when(userService.findByUserNameIgnoreCase("johny_")).thenReturn(user);

        // Mock the behavior of the JWT utility
        String mockToken = "mock-jwt-token";
        when(jwtUtil.generateToken("johny_", "User")).thenReturn(mockToken);

        // Prepare the request payload
        Map<String, String> myMap = new HashMap<>();
        myMap.put("userName", "johny_");
        myMap.put("mfaCode", "222365064");

        // Perform the POST request and validate the response
        mockMvc.perform(post("/v1/user/validateCode")
                        .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
                        .content(new ObjectMapper().writeValueAsString(myMap)))  // Convert map to JSON string
                .andExpect(status().isOk())  // Expect status OK (200)
                .andExpect(jsonPath("$.message").value("MFA validation successful"))  // Validate response message
                .andExpect(jsonPath("$.token").value(mockToken));  // Validate the generated token
    }


    }

