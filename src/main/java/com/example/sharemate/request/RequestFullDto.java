package com.example.sharemate.request;

import com.example.sharemate.item.ItemRequestAnswerDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RequestFullDto {
    private Long id;
    private String requester;
    private String description;
    private List<ItemRequestAnswerDto> items;
}
