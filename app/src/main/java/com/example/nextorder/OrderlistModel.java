package com.example.nextorder;

public class OrderlistModel {
    String UserName;
    String DishImage;
    String Dishname;
    String DishPrice;
    String Quantity;

    public OrderlistModel(String userName, String dishImage, String dishname, String dishPrice, String quantity) {
        UserName = userName;
        DishImage = dishImage;
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

    public String getDishImage() {
        return DishImage;
    }

    public void setDishImage(String dishImage) {
        DishImage = dishImage;
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
