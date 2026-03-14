package com.example.sharemate.request;


import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RequestRepository requestRepository;


    User user;

    @BeforeAll
    void createUserForTests() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Name");
        userCreateDto.setEmail("email@gmail.com");
        String json = objectMapper.writeValueAsString(userCreateDto);

        String responseJson = mockMvc.perform(post("/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(userCreateDto.getName()))
                .andExpect(jsonPath("$.email").value(userCreateDto.getEmail()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        user = objectMapper.readValue(responseJson, User.class);
    }

    static int randomNum = 0;

    @Test
    void createRequest_shouldReturnOk_whenRequestDidNotCreateFromUser() throws Exception {
        String json;
        RequestCreateDto requestCreateDto = new RequestCreateDto();
        requestCreateDto.setDescription("item Description " + randomNum++);
        json = objectMapper.writeValueAsString(requestCreateDto);
        mockMvc.perform(post("/requests")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.description").value(requestCreateDto.getDescription()))
                .andExpect(jsonPath("$.created").exists());

    }

    @Test
    void createRequest_shouldReturnConfilct_whenRequestCreatedFromUser() throws Exception {
        String json;
        RequestCreateDto requestCreateDto = new RequestCreateDto();
        requestCreateDto.setDescription("item Description ");
        json = objectMapper.writeValueAsString(requestCreateDto);
        Request request = new Request();
        request.setUser(user);
        request.setDescription(requestCreateDto.getDescription());
        requestRepository.save(request);
        mockMvc.perform(post("/requests")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    void getAllRequestsFromUser_shouldReturnListOfRequests_whenRequestsAreExist() throws Exception {
        Request request1 = requestCreateForTests();
        Request request2 = requestCreateForTests();
        Request request3 = requestCreateForTests();
        mockMvc.perform(get("/requests/all")
                        .param("from","0")
                        .param("size","20")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[2].id").value(request1.getId()))
                .andExpect(jsonPath("$[2].description").value(request1.getDescription()))
                .andExpect(jsonPath("$[2].created").exists())
                .andExpect(jsonPath("$[2].user.id").value(user.getId()))
                .andExpect(jsonPath("$[1].id").value(request2.getId()))
                .andExpect(jsonPath("$[1].description").value(request2.getDescription()))
                .andExpect(jsonPath("$[1].created").exists())
                .andExpect(jsonPath("$[1].user.id").value(user.getId()))
                .andExpect(jsonPath("$[0].id").value(request3.getId()))
                .andExpect(jsonPath("$[0].description").value(request3.getDescription()))
                .andExpect(jsonPath("$[0].created").exists())
                .andExpect(jsonPath("$[0].user.id").value(user.getId()));
    }

    private Request requestCreateForTests() throws Exception {
        String json;
        RequestCreateDto requestCreateDto = new RequestCreateDto();
        requestCreateDto.setDescription("item Description " + randomNum++);
        json = objectMapper.writeValueAsString(requestCreateDto);
        String responseJson = mockMvc.perform(post("/requests")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.description").value(requestCreateDto.getDescription()))
                .andExpect(jsonPath("$.created").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(responseJson, Request.class);
    }
}
