package com.kashish.ordering_system_backend.repo;

import com.kashish.ordering_system_backend.entities.menu;
import com.kashish.ordering_system_backend.entities.orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repo extends JpaRepository<menu, Integer> {
menu findByName (String name);
}
