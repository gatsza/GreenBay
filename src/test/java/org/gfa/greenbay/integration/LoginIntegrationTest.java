package org.gfa.greenbay.integration;


import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginIntegrationTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void successfulLoginTest_allParameterRight() throws Exception {
    User testUser = userRepository.findById(1L).get();

    LoginRequestDTO testRequest =
        new LoginRequestDTO(testUser.getUsername(), testUser.getPassword());

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("token", any(String.class)));
  }

  @Test
  public void errorLoginTest_usernameIsMissing() throws Exception {
    User testUser = userRepository.findById(1L).get();

    LoginRequestDTO testRequest =
        new LoginRequestDTO(null, testUser.getPassword());

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Login information is required: username!")));
  }

  @Test
  public void errorLoginTest_passwordIsMissing() throws Exception {
    User testUser = userRepository.findById(1L).get();

    LoginRequestDTO testRequest =
        new LoginRequestDTO(testUser.getUsername(), null);

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Login information is required: password!")));
  }

  @Test
  public void errorLoginTest_logininformationIsMissing() throws Exception {
    LoginRequestDTO testRequest =
        new LoginRequestDTO(null, null);

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Login information is required: username and password!")));
  }

  @Test
  public void errorLoginTest_payloadIsMissing() throws Exception {
    mockMvc.perform(post("/login")
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Login information is required: username and password!")));
  }

  @Test
  public void errorLoginTest_wrongUserData() throws Exception {
    LoginRequestDTO testRequest =
        new LoginRequestDTO("WrongUser","WrongPassword");

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("error", is("Incorrect username or password!")));
  }
}
