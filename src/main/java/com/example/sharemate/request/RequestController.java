package com.example.sharemate.request;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public Request create(@RequestBody RequestCreateDto requestCreateDto, @RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return requestService.create(requestCreateDto,userId);
    }

    @GetMapping("/all")
    public List<Request> getAllRequestsFromUser(
            @RequestParam (defaultValue = "0",required = false)Integer from,
            @RequestParam(defaultValue = "20",required = false) Integer size,
            @RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return requestService.getAllRequestsFromUser(from,size,userId);
    }

}


