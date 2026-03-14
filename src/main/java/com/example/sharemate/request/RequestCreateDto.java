package com.example.sharemate.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestCreateDto {
    @NotBlank
    private String description;
}
