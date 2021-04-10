package com.example.nextorder;

public class OrderModel {
    String oid;
    String OrderName;
    String OrderTime;

    public OrderModel(String oid, String orderName, String orderTime) {
        this.oid = oid;
        OrderName = orderName;
        OrderTime = orderTime;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

}
