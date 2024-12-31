package com.example.User;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private AdminController adminController;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void returnAllUser()throws Exception{
        UserEntity mockUser1 = new UserEntity();
        mockUser1.setId(1);
        mockUser1.setName("Muhamed");
        mockUser1.setSurname("Cakaj");
        mockUser1.setUserName("meticakaj");
        mockUser1.setEmail("muhamedcakaj57@gmail.com");
        mockUser1.setPassword("Muhamed10");
        mockUser1.setRole("User");

        UserEntity mockUser2 = new UserEntity();
        mockUser2.setId(1);
        mockUser2.setName("Muhamed");
        mockUser2.setSurname("Cakaj");
        mockUser2.setUserName("meticakaj");
        mockUser2.setEmail("muhamedcakaj57@gmail.com");
        mockUser2.setPassword("Muhamed10");
        mockUser2.setRole("User");

        List<UserEntity> mockUser = new ArrayList<>();
        mockUser.add(mockUser1);
        mockUser.add(mockUser2);

        when(userService.findAll()).thenReturn(mockUser);


        mockMvc.perform(get("/v1/admin"))
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

        mockMvc.perform(post("/v1/admin")
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
        mockMvc.perform(put("/v1/admin/{userName}", userToCreate.getUserName())  // Use the actual username
                        .contentType(MediaType.APPLICATION_JSON)  // Set content type
                        .content(new ObjectMapper().writeValueAsString(myMap)))  // Convert map to JSON string for request body
                .andExpect(status().isOk());  // Expect status OK (200)
    }
    @Test
    public void deleteUser() throws Exception{
        UserEntity user=new UserEntity();
        user.setId(1042);
        user.setName("Afrim");
        user.setSurname("Cakaj");
        user.setUserName("afrimcakaj");
        user.setEmail("muhametcakaj57@gmail.com");
        user.setPassword("Afrim10");
        user.setRole("USER");

        userService.save(user);

        doNothing().when(userService).deleteById(1042);

        // Perform the DELETE request
        mockMvc.perform(delete("/v1/admin/{id}",user.getId()))
                .andExpect(status().isNoContent()); // Expect

    }

}
