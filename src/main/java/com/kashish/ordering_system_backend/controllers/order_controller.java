package com.kashish.ordering_system_backend.controllers;

import com.kashish.ordering_system_backend.entities.customer;
import com.kashish.ordering_system_backend.entities.orders;
import com.kashish.ordering_system_backend.repo.customer_repo;
import com.kashish.ordering_system_backend.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/order")
public class order_controller {
  private final  service service_obj;
    @Autowired
    private customer_repo customer_repo_obj;
    public order_controller(service serviceObj) {
        service_obj = serviceObj;
    }
// for customer
    @PostMapping("/add_order")
    public orders add_order(@RequestBody orders order){
        return service_obj.add_order(order);

    }
    // for admins
    @GetMapping("/all_orders")
    public List<orders> get_all_orders(){
        return service_obj.get_all_orders();
    }
 // for customer
    @GetMapping("/orders/{st_id}")
    public List<orders> individual_customer_orders_by_id(@PathVariable Integer st_id , Authentication authentication){
       String email = authentication.getName();
        customer loggedInCustomer =
                customer_repo_obj.findByEmail(email);
        if((loggedInCustomer.getSt_id().equals(st_id))) {


            return service_obj.get_individual_customer_orders_by_id(st_id);
        }
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You cannot access another student's orders!"
        );
    }
    @PostMapping("/complete_order/{order_id}")
    public  orders complete_order (@PathVariable Integer order_id){
        return service_obj.complete_order(order_id);
    }
}
