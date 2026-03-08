package com.example.sharemate.item.model;

import com.example.sharemate.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
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
    private Boolean available;


}
