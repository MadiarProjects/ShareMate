package com.example.sharemate.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByUser_Id(Long userId, Pageable pageable);

    boolean existsRequestsByDescriptionAndUser_Id(String description, Long userId);
}
