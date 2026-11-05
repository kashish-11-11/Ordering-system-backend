package com.kashish.ordering_system_backend.repo;

import com.kashish.ordering_system_backend.entities.orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface order_repo extends JpaRepository<orders, Integer> {
    @Query("SELECT o FROM orders o WHERE o.st_id = :stId")
    List<orders> find_by_stid(@Param("stId") Integer st_id);
}
