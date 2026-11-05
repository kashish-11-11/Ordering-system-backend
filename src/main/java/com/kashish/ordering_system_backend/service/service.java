package com.kashish.ordering_system_backend.service;

import com.kashish.ordering_system_backend.entities.customer;
import com.kashish.ordering_system_backend.entities.menu;
import com.kashish.ordering_system_backend.entities.orders;
import com.kashish.ordering_system_backend.repo.customer_repo;
import com.kashish.ordering_system_backend.repo.order_repo;
import com.kashish.ordering_system_backend.repo.repo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class service {
    private final repo repo_obj;
    private final order_repo order_repo_obj;
private final customer_repo customer_repo_obj;
    public service(repo repo_obj, order_repo orderRepoObj, customer_repo customer_repo_objj) {
        this.repo_obj = repo_obj;
        order_repo_obj = orderRepoObj;
      customer_repo_obj = customer_repo_objj;
    }

    public List<menu> display_menu_items() {
       return  repo_obj.findAll();

    }
    public menu add_item(menu menu_obj){
     return repo_obj.save(menu_obj);
    }

    public void delete_item(Integer id) {

        repo_obj.deleteById(id);
    }

    public menu update_item(Integer id, menu menuObj) {
        menu item = repo_obj.findById(id).get();
        item.setName(menuObj.getName());
        item.setPrice(menuObj.getPrice());
        return repo_obj.save(item);



    }

    public orders add_order(orders order) {
//    Integer item_price = repo_obj.findByName(order.getItem_name()).getId();
        Integer item_price  = order.getItem_price();
        Integer quantity = order.getQuantity();
    Integer total_amount =    quantity  * item_price;
    order.setOrder_time(LocalDateTime.now());
    order.setTotal_amount(total_amount);
    order.setStatus("Pending");
      return   order_repo_obj.save(order);
    }

    public List<orders> get_all_orders() {
     return   order_repo_obj.findAll();
    }

    public List<orders> get_individual_customer_orders_by_id(Integer stId) {
     return order_repo_obj.find_by_stid(stId);
    }

    public orders complete_order(Integer orderId) {
orders order_obj = order_repo_obj.findById(orderId)   .orElseThrow(() -> new RuntimeException("Order not found"));

order_obj.setStatus("Completed");
        return order_repo_obj.save(order_obj);
    }

    public Optional<customer> get_profile_info(int stId) {
return customer_repo_obj.findById(stId);
    }
}
