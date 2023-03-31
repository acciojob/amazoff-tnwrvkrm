package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderMap;
    HashMap<String, DeliveryPartner> deliveryPartnerMap;
    HashMap<String , String> orderPartnerMap;
    HashMap<String , List<String>> partnerOrderMap;

    public OrderRepository() {
        orderMap = new HashMap<>();
        deliveryPartnerMap = new HashMap<>();
        orderPartnerMap = new HashMap<>();
        partnerOrderMap = new HashMap<>();
    }

    //1
    public ResponseEntity<String> addOrder(Order order){
        orderMap.put(order.getId(), order);

        return new ResponseEntity<>("Added the Order", HttpStatus.CREATED);
    }

    //2
    public ResponseEntity<String> addPartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerMap.put(partnerId, deliveryPartner);
        partnerOrderMap.put(partnerId, new ArrayList<>());

        return new ResponseEntity<>("Added the Delivery Partner", HttpStatus.CREATED);
    }

    //3
    public ResponseEntity<String> addOrderPartnerPair(String orderId, String partnerId){
        DeliveryPartner deliveryPartner = deliveryPartnerMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        orderPartnerMap.put(orderId, partnerId);
        partnerOrderMap.get(partnerId).add(orderId);

        return new ResponseEntity<>("Added the pair", HttpStatus.CREATED);
    }

    //4
    public ResponseEntity<Order> getOrderById(String orderId){
        return new ResponseEntity<>(orderMap.get(orderId), HttpStatus.FOUND);
    }

    //5
    public ResponseEntity<DeliveryPartner> getPartnerById(String partnerId){
        return new ResponseEntity<>(deliveryPartnerMap.get(partnerId),HttpStatus.FOUND);
    }

    //6
    public ResponseEntity<Integer> getOrderCountByPartnerId(String partnerId){
        return new ResponseEntity<>(partnerOrderMap.get(partnerId).size(),HttpStatus.FOUND);
    }

    //7
    public ResponseEntity<List<String>> getOrdersByPartnerId(String partnerId){
        return new ResponseEntity<>(partnerOrderMap.get(partnerId), HttpStatus.FOUND);
    }

    //8
    public ResponseEntity<List<String>> getAllOrders(){
        return new ResponseEntity<>(new ArrayList<>(orderMap.keySet()), HttpStatus.FOUND);
    }

    //9
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        int unassignedOrders = orderMap.size() - orderPartnerMap.size();
        return new ResponseEntity<>(unassignedOrders, HttpStatus.FOUND);
    }

    //10
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int checkTime = convertDeliveryTimeToInt(time);
        int answer = 0;
        for(String orderId : partnerOrderMap.get(partnerId)){
            if(orderMap.get(orderId).getDeliveryTime() > checkTime){
                answer++;
            }
        }
        return new ResponseEntity<>(answer, HttpStatus.FOUND);
    }

    private int convertDeliveryTimeToInt(String deliveryTime){
        String time[] = deliveryTime.split(":");

        return Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]);
    }

    //11
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(String partnerId){
        int lastTime = 0;

        for(String orderId : partnerOrderMap.get(partnerId)){
            int currOrderTime = orderMap.get(orderId).getDeliveryTime();
            if(currOrderTime > lastTime){
                lastTime = currOrderTime;
            }
        }

        String lastTimeString = convertDeliveryTimeToString(lastTime);

        return new ResponseEntity<>(lastTimeString, HttpStatus.FOUND);
    }

    private String convertDeliveryTimeToString(int deliveryTime){
        int hh = deliveryTime / 60;
        int mm = deliveryTime % 60;

        String stringTime = "";

        if(hh < 10){
            stringTime = "0" + hh+ ":";
        }
        else {
            stringTime += hh + ":";
        }

        if(mm < 10){
            stringTime += "0" + mm;
        }
        else {
            stringTime += mm;
        }
        return stringTime;
    }

    //12
    public ResponseEntity<String> deletePartnerById(String partnerId){
        for(String orderId : partnerOrderMap.get(partnerId)){
            orderPartnerMap.remove(orderId);
        }

        partnerOrderMap.remove(partnerId);
        deliveryPartnerMap.remove(partnerId);

        return new ResponseEntity<>("Partner Details Deleted", HttpStatus.OK);
    }

    //13
    public ResponseEntity<String> deleteOrderById(String orderId){
        if(orderPartnerMap.containsKey(orderId)){
            partnerOrderMap.get(orderPartnerMap.get(orderId)).remove(orderId);
        }

        orderPartnerMap.remove(orderId);
        orderMap.remove(orderId);

        return new ResponseEntity<>("Order Details Deleted", HttpStatus.OK);
    }
}
