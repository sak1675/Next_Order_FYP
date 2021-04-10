package com.example.nextorder;

public class MenuModel {
    String DishName;
    String DishImage;
    String DishPrice;
    String DishDescription;

    public MenuModel(String dishName, String dishImage, String dishPrice, String dishDescription) {
        DishName = dishName;
        DishImage = dishImage;
        DishPrice = dishPrice;
        DishDescription = dishDescription;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishImage() {
        return DishImage;
    }

    public void setDishImage(String dishImage) {
        DishImage = dishImage;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getDishDescription() {
        return DishDescription;
    }

    public void setDishDescription(String dishDescription) {
        DishDescription = dishDescription;
    }



}
