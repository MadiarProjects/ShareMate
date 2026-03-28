package com.example.sharemate.item;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequestAnswerDto {
    private Long id;
    private String description;
    private Long requestId;
    private boolean available;
}
