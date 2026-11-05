package com.kashish.ordering_system_backend.service;

import com.kashish.ordering_system_backend.entities.customer;
import com.kashish.ordering_system_backend.repo.customer_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class my_user_detail_service implements UserDetailsService {
    @Autowired
     private customer_repo customer_repo_obj;
    @Override
    public UserDetails loadUserByUsername (String email){
        customer c = customer_repo_obj.findByEmail(email);
        if(c == null){
            System.out.println("-----> FAILED: User was NOT FOUND in the database!");
            throw new UsernameNotFoundException("User not found");

        }
        System.out.println("-----> SUCCESS: Found user in database: " + c.getCustomer_name());
        System.out.println("-----> Database Password is: '" + c.getPass() + "'");
return User.builder().username(c.getEmail()).password(c.getPass()).roles(c.getRole()).build();
    }

}
