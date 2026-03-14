package com.example.sharemate.request;


import com.example.sharemate.item.model.Item;
import com.example.sharemate.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    private Timestamp created=Timestamp.valueOf(LocalDateTime.now());
}
