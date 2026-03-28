package com.example.sharemate.item;

import com.example.sharemate.request.Request;
import com.example.sharemate.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private boolean available;
    @ManyToOne
    @JoinColumn(name = "item_request_id")
    @JsonIgnore
    private Request request;
}
