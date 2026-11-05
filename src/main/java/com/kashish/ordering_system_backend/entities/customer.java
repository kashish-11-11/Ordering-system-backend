package com.kashish.ordering_system_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer st_id;
    private String customer_name;
    @Column (unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pass;
    @Column(nullable = false)
    private String role ;



}
