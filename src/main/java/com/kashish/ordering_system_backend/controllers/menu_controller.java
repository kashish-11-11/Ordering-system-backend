package com.kashish.ordering_system_backend.controllers;

import com.kashish.ordering_system_backend.entities.customer;
import com.kashish.ordering_system_backend.entities.menu;
import com.kashish.ordering_system_backend.repo.customer_repo;
import com.kashish.ordering_system_backend.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class menu_controller {


    public menu_controller(service service_obj) {
        this.service_obj = service_obj;
    }
    @Autowired
    private customer_repo customer_repo_obj;
    private final service service_obj;
    @GetMapping("/menu")
    public List<menu> display_menu(){
     return service_obj.display_menu_items();

    }
    @PostMapping("add_item")
    public menu add_item(@RequestBody menu menu_obj ){
        return service_obj.add_item(menu_obj);

    }
   @DeleteMapping("delete_item/{id}")
    public void delete_item(@PathVariable Integer id){
        service_obj.delete_item(id);

   }
    @PutMapping("update_item/{id}")
    public void update_item(@PathVariable Integer id, @RequestBody menu menu_obj){
        service_obj.update_item(id, menu_obj);

    }
    @GetMapping("/profile/{st_id}")
    public Optional<customer> get_profile_info(@PathVariable int st_id, Authentication authentication) {

        String email = authentication.getName();
        customer loggedInCustomer =
                customer_repo_obj.findByEmail(email);
        if ((loggedInCustomer.getSt_id().equals(st_id))) {
            return service_obj.get_profile_info(st_id);
        }
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You cannot access another student's orders!"
        );
    }

}
