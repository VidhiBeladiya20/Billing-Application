package com.example.Bill_System.repository;

import com.example.Bill_System.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer ,Long> {
}
