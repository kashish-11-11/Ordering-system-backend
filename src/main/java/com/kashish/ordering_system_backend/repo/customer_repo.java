package com.kashish.ordering_system_backend.repo;

import com.kashish.ordering_system_backend.entities.customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface customer_repo extends JpaRepository<customer, Integer> {
customer findByEmail(String email);
}
