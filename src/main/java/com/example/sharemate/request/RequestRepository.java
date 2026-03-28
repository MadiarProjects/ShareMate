package com.example.sharemate.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsRequestsByDescriptionAndUser_Id(String description, Long userId);
}
