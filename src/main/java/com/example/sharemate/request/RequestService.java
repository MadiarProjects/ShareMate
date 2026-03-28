package com.example.sharemate.request;


import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.Item;
import com.example.sharemate.item.ItemRequestAnswerDto;
import com.example.sharemate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;

    @Transactional
    public Request create(RequestCreateDto requestCreateDto, Long userId) {
        if (requestRepository.existsRequestsByDescriptionAndUser_Id(requestCreateDto.getDescription(), userId)) {
            throw new AlreadyExistException("user has same request");
        }
        Request request = new Request();
        request.setUser(userService.getById(userId));
        request.setDescription(requestCreateDto.getDescription());
        return requestRepository.save(request);
    }

    public List<RequestShortDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by( "id")
        );
        List<Request> requests = requestRepository.findAll(pageable).getContent();
        return requests
                .stream()
                .map(request -> {
                    RequestShortDto requestResponseDto = new RequestShortDto();
                    requestResponseDto.setRequester(request.getUser().getName());
                    requestResponseDto.setId(request.getId());
                    requestResponseDto.setDescription(request.getDescription());
                    return requestResponseDto;
                }).toList();
    }

    @Transactional
    public RequestFullDto getById(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundedException("request at " + id + " does not exists"));
        RequestFullDto requestFullDto = new RequestFullDto();
        requestFullDto.setId(request.getId());
        requestFullDto.setDescription(request.getDescription());
        requestFullDto.setRequester(request.getUser().getName());
        requestFullDto.setItems(request.getItems().stream()
                .map(item -> {
                    return new ItemRequestAnswerDto(item.getId(), item.getDescription(), request.getId(), item.isAvailable());
                }).toList());
        return requestFullDto;
    }

    @Transactional
    public void addItemToRequest(Item item) {
        Request request = requestRepository.findById(item.getRequest().getId())
                .orElseThrow(() -> new NotFoundedException("request at " + item.getRequest().getId() + " does not exists"));
        request.getItems().add(item);
        requestRepository.save(request);
    }
}
