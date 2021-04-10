package com.example.nextorder;

public class OrderingModel {
    String UserName;
    String Dishname;
    String DishPrice;
    String Quantity;

    public OrderingModel(String userName, String dishname, String dishPrice, String quantity) {
        UserName = userName;
        Dishname = dishname;
        DishPrice = dishPrice;
        Quantity = quantity;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDishname() {
        return Dishname;
    }

    public void setDishname(String dishname) {
        Dishname = dishname;
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
}
