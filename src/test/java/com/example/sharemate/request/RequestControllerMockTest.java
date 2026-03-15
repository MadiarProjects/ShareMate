package com.example.sharemate.request;


import com.example.sharemate.user.controller.UserController;
import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.model.User;
import com.example.sharemate.user.repository.UserRepository;
import com.example.sharemate.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({RequestController.class, UserController.class})
public class RequestControllerMockTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;
    @MockitoBean
    RequestService requestService;
    @Test
    void createRequest_shouldReturnOk_whenRequestDidNotCreateFromUser()throws Exception{
        UserCreateDto userCreateDto=new UserCreateDto();
        userCreateDto.setName("Name");
        userCreateDto.setEmail("email@gmail.com");
//        String json = objectMapper.writeValueAsString(userCreateDto);
        User user=new User();
        user.setId(1L);
        user.setEmail(userCreateDto.getEmail());
        user.setName(userCreateDto.getName());
        Mockito.when(userService.create(userCreateDto))
                .thenReturn(user);
//         mockMvc.perform(post("/users").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.email").value(user.getEmail()));

        RequestCreateDto requestCreateDto=new RequestCreateDto();
        requestCreateDto.setDescription("item Description ");
        Request request=new Request();
        request.setId(1L);
        request.setUser(user);
        request.setDescription(requestCreateDto.getDescription());

       String json=objectMapper.writeValueAsString(requestCreateDto);
        Mockito.when(requestService.create(requestCreateDto,user.getId())).thenReturn(request);
        mockMvc.perform(post("/requests")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id",user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getId()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.description").value(requestCreateDto.getDescription()))
                .andExpect(jsonPath("$.created").exists());

    }
}
