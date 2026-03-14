package com.example.sharemate.request;


import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;

    public Request create(RequestCreateDto requestCreateDto, Long userId) {
        if (requestRepository.existsRequestsByDescriptionAndUser_Id(requestCreateDto.getDescription(), userId)) {
            throw new AlreadyExistException("user has same request");
        }
        Request request = new Request();
        request.setUser(userService.getById(userId));
        request.setDescription(requestCreateDto.getDescription());
        return requestRepository.save(request);
    }

    public List<Request> getAllRequestsFromUser(Integer from, Integer size, Long userId) {
        userService.getById(userId);

        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by(Sort.Direction.DESC, "created")
        );
        return requestRepository.findAllByUser_Id(userId, pageable);
    }

}
