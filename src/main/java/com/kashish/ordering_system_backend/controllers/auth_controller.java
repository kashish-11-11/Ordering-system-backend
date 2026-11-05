package com.kashish.ordering_system_backend.controllers;

import com.kashish.ordering_system_backend.dto.login_request;
import com.kashish.ordering_system_backend.dto.register_request;
import com.kashish.ordering_system_backend.entities.customer;
import com.kashish.ordering_system_backend.repo.customer_repo;
import com.kashish.ordering_system_backend.service.jwt_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class auth_controller {
  @Autowired
  private customer_repo  customer_repo_obj;
  @Autowired
    private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager_obj;
  @Autowired
  private jwt_service jwt_service_obj;
  @PostMapping("/register")
    public Map<String,Object> register (@RequestBody register_request register_request_obj){
      customer c = new customer();
      c.setCustomer_name(register_request_obj.getCustomer_name());
      c.setEmail(register_request_obj.getEmail());
      c.setPass(passwordEncoder.encode(register_request_obj.getPass()));

c.setRole(register_request_obj.getRole());
    String token = jwt_service_obj.generate_token(register_request_obj.getEmail());
    customer saved_customer =   customer_repo_obj.save(c);
    return Map.of("st_id",saved_customer.getSt_id() , "token", token );


  }
  @PostMapping("/login")
  public Map<String, String> login (@RequestBody login_request login_request_obj){
    Authentication authenticatio_obj = authenticationManager_obj.authenticate(new UsernamePasswordAuthenticationToken(login_request_obj.getEmail(), login_request_obj.getPass()));
    customer loggedInCustomer =
            customer_repo_obj.findByEmail(login_request_obj.getEmail());
    System.out.println("Entered ID: " + login_request_obj.getSt_id());
    System.out.println("Database ID: " + loggedInCustomer.getSt_id());
    System.out.println("IDs equal: " +
            loggedInCustomer.getSt_id().equals(login_request_obj.getSt_id()));
    if (loggedInCustomer == null ||
            !loggedInCustomer.getSt_id().equals(login_request_obj.getSt_id())) {

      throw new ResponseStatusException(
              HttpStatus.UNAUTHORIZED,
              "Invalid Student ID"
      );
    }
    String token = jwt_service_obj.generate_token(login_request_obj.getEmail());
    return Map.of("message", "Login successful", "token" , token , "st_id" ,String.valueOf(loggedInCustomer.getSt_id()));

  }
  @GetMapping("/test")
  public String test(){
    return "Jwt authentication";

  }

}
