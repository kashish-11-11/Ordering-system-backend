package com.kashish.ordering_system_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class register_request {
    private String customer_name;
    private String email;
    private String pass;
    private String role;
}
