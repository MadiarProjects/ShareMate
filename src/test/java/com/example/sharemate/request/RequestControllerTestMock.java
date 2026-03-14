//package com.example.sharemate.request;
//
//
//import com.example.sharemate.user.model.User;
//import com.example.sharemate.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import tools.jackson.databind.ObjectMapper;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(RequestController.class)
//public class RequestControllerTestMock {
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockitoBean
//    RequestRepository requestRepository;
//    @MockitoBean
//    ObjectMapper objectMapper;
//
//
//    @Test
//    void createRequest_shouldReturnOk_whenRequestDidNotCreateFromUser()throws Exception{
//        User user=new User();
//        user.setId(1L);
//        user.setName("Name");
//        user.setEmail("email@gmail.com");
//        String json = objectMapper.writeValueAsString(user);
//
//        String responseJson = mockMvc.perform(post("/users").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.email").value(user.getEmail()))
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        user=objectMapper.readValue(responseJson,User.class);
//        RequestCreateDto requestCreateDto=new RequestCreateDto();
//        requestCreateDto.setDescription("item Description ");
//        json=objectMapper.writeValueAsString(requestCreateDto);
//        mockMvc.perform(post("/requests")
//                        .header("userId",user.getId())
//                        .content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value())
//                .andExpect("$")
//                .andExpect("$")
//                .andExpect("$")
//                .andExpect("$")
//                .andExpect("$")
//
//
//    }
//}
