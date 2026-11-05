package com.kashish.ordering_system_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class login_request {
private String email;
private String pass;
private Integer st_id;
}
