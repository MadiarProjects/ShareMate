package com.example.sharemate.request;


import com.example.sharemate.item.Item;
import com.example.sharemate.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

@Entity
@Table(name = "item_requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private User user;
    private String description;
    @OneToMany(mappedBy = "request")
    private List<Item> items=new ArrayList<>();
}
