package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    //1
    public ResponseEntity<String> addOrder(Order order){
        return orderRepository.addOrder(order);
    }

    //2
    public ResponseEntity<String> addPartner(String partnerId){
        return orderRepository.addPartner(partnerId);
    }

    //3
    public ResponseEntity<String> addOrderPartnerPair(String orderId, String partnerId){
        return orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    //4
    public ResponseEntity<Order> getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }

    //5
    public ResponseEntity<DeliveryPartner> getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }

    //6
    public ResponseEntity<Integer> getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    //7
    public ResponseEntity<List<String>> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    //8
    public ResponseEntity<List<String>> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    //9
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    //10
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    //11
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    //12
    public ResponseEntity<String> deletePartnerById(String partnerId){
        return orderRepository.deletePartnerById(partnerId);
    }

    //13
    public ResponseEntity<String> deleteOrderById(String orderId){
        return orderRepository.deleteOrderById(orderId);
    }
}
