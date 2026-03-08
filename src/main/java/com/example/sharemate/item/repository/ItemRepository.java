package com.example.sharemate.item.repository;

import com.example.sharemate.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long ownerId);

    @Query("""
            select i from Item i where (upper(i.name) like upper(concat('%',:text,'%')) or upper(i.description) like upper(concat('%',:text,'%'))) and i.available=true
            """)
    List<Item> findAllByDescription(String text);
}
