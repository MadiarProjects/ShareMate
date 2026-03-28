package com.example.sharemate.item;

import com.example.sharemate.comment.Comment;
import com.example.sharemate.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class ItemWithCommentsDto {
    private Long id;
    private String name;
    private String description;
    private User owner;
    private Boolean available;
    private List<Comment>comments;
}
