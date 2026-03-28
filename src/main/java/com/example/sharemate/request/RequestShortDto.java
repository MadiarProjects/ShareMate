package com.example.sharemate.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestShortDto {
    private Long id;
    private String requester;
    private String description;
}
