package com.example.Bill_System.repository;

import com.example.Bill_System.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
