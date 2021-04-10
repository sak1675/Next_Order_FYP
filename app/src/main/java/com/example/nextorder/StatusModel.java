package com.example.nextorder;

public class StatusModel {
    String Oid;
    String UserName;
    String DishName;
    String DishPrice;
    String Quantity;
    String CustomOrder;
    String OrderDate;
    String BeingPrepared;
    String OrderReady;

    public StatusModel(String oid, String userName, String dishName, String dishPrice, String quantity, String customOrder, String orderDate, String beingPrepared, String orderReady) {
        Oid = oid;
        UserName = userName;
        DishName = dishName;
        DishPrice = dishPrice;
        Quantity = quantity;
        CustomOrder = customOrder;
        OrderDate = orderDate;
        BeingPrepared = beingPrepared;
        OrderReady = orderReady;
    }

    public StatusModel(String oid, String userName, String dishName, String dishPrice, String quantity, String customOrder, String orderDate, String beingPrepared) {

    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCustomOrder() {
        return CustomOrder;
    }

    public void setCustomOrder(String customOrder) {
        CustomOrder = customOrder;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getBeingPrepared() {
        return BeingPrepared;
    }

    public void setBeingPrepared(String beingPrepared) {
        BeingPrepared = beingPrepared;
    }

    public String getOrderReady() {
        return OrderReady;
    }

    public void setOrderReady(String orderReady) {
        OrderReady = orderReady;
    }
}
