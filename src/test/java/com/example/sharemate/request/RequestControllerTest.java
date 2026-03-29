package com.example.sharemate.request;


import com.example.sharemate.item.Item;
import com.example.sharemate.item.ItemCreateDto;
import com.example.sharemate.item.ItemRepository;
import com.example.sharemate.item.ItemRequestAnswerDto;
import com.example.sharemate.user.UserCreateDto;
import com.example.sharemate.user.User;
import com.example.sharemate.user.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
public class RequestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void createUserForTests()  {
        User createUser=new User();
        createUser.setName("name"+randomNum++);
        createUser.setEmail("email"+randomNum++);
        user =userRepository.save(createUser);
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
                .andExpect(jsonPath("$.description").value(requestCreateDto.getDescription()));
    }

    @Test
    void createRequest_shouldReturnConfilct_whenRequestAlreadyExistsFromUser() throws Exception {
        String json;
        RequestCreateDto requestCreateDto = new RequestCreateDto();
        requestCreateDto.setDescription("item Description " + randomNum++);
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
        RequestShortDto request1 = mapperToShortDto(requestCreateForTests());
        RequestShortDto request2 = mapperToShortDto(requestCreateForTests());
        RequestShortDto request3 = mapperToShortDto(requestCreateForTests());
        mockMvc.perform(get("/requests/all")
                        .param("from", "0")
                        .param("size", "20")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(request1.getId()))
                .andExpect(jsonPath("$[0].description").value(request1.getDescription()))
                .andExpect(jsonPath("$[0].requester").value(user.getName()))
                .andExpect(jsonPath("$[1].id").value(request2.getId()))
                .andExpect(jsonPath("$[1].description").value(request2.getDescription()))
                .andExpect(jsonPath("$[1].requester").value(user.getName()))
                .andExpect(jsonPath("$[2].id").value(request3.getId()))
                .andExpect(jsonPath("$[2].description").value(request3.getDescription()))
                .andExpect(jsonPath("$[2].requester").value(user.getName()));
    }

    @Test
    void getAllRequestsFromUser_shouldReturnEmptyList_whenRequestsAreNotExist() throws Exception {
        mockMvc.perform(get("/requests/all")
                        .param("from", "0")
                        .param("size", "20")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getById_shouldReturnRequestWithFullInformation_whenRequestIdGivenCorrectly() throws Exception {
        Request request = requestCreateForTests();
        RequestFullDto requestFullDto = mapperToFullDto(request);
        ItemCreateDto itemCreateDto=new ItemCreateDto();
        itemCreateDto.setName("name Item");
        itemCreateDto.setAvailable(true);
        itemCreateDto.setDescription("Item Description");
        itemCreateDto.setRequestId(request.getId());
        String json=objectMapper.writeValueAsString(itemCreateDto);

        String itemResponse=mockMvc.perform(post("/items")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(itemCreateDto.getName()))
                .andExpect(jsonPath("$.description").value(itemCreateDto.getDescription()))
                .andExpect(jsonPath("$.owner.id").value(user.getId()))
                .andExpect(jsonPath("$.available").value(true))
                .andReturn()
                .getResponse()
                .getContentAsString();
//        Item item = new Item();
//        item.setName("name");
//        item.setRequest(request);
//        item.setOwner(user);
//        item.setAvailable(true);
//        item.setDescription("Description" );
//        itemRepository.save(item);
        Item item=objectMapper.readValue(itemResponse,Item.class);
        ItemRequestAnswerDto itemRequestAnswerDto = new ItemRequestAnswerDto(
                item.getId(),
                item.getDescription(),
                request.getId(),
                item.getAvailable()
        );
        mockMvc.perform(get("/requests/" + requestFullDto.getId()))
                .andExpect(jsonPath("$.id").value(requestFullDto.getId()))
                .andExpect(jsonPath("$.requester").value(user.getName()))
                .andExpect(jsonPath("$.description").value(requestFullDto.getDescription()))
                .andExpect(jsonPath("$.items.size()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(itemRequestAnswerDto.getId()))
                .andExpect(jsonPath("$.items[0].description").value(itemRequestAnswerDto.getDescription()))
                .andExpect(jsonPath("$.items[0].requestId").value(requestFullDto.getId()))
                .andExpect(jsonPath("$.items[0].available").value(true));
    }

    @Test
    void getById_shouldReturnNotFounded_whenRequestIdGivenWrong() throws Exception {
        mockMvc.perform(get("/requests/" + ++randomNum))
                .andExpect(status().isNotFound());
    }


    private RequestShortDto mapperToShortDto(Request request) {
        RequestShortDto requestShortDto = new RequestShortDto();
        requestShortDto.setId(request.getId());
        requestShortDto.setRequester(request.getUser().getName());
        requestShortDto.setDescription(request.getDescription());
        return requestShortDto;
    }

    private RequestFullDto mapperToFullDto(Request request) {
        RequestFullDto requestFullDto = new RequestFullDto();
        requestFullDto.setId(request.getId());
        requestFullDto.setRequester(request.getUser().getName());
        requestFullDto.setDescription(request.getDescription());
        requestFullDto.setItems(request.getItems().stream()
                .map(item -> {
                    return new ItemRequestAnswerDto(item.getId(),
                            item.getDescription(),
                            item.getRequest().getId(),
                            item.getAvailable());
                }).toList());
        return requestFullDto;
    }

    private Request requestCreateForTests() throws Exception {
        Request request=new Request();
        request.setDescription("Description"+randomNum++);
        request.setUser(user);
        return requestRepository.save(request);
    }
}
