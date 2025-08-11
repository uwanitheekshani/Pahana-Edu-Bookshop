package com.example.pahanabillingsystem.model;

public class Item {

    private int Id;
    private String itemName;
    private String description;
    private String unitPrice;
    private int quantity;

    public Item() {
    }

    public Item(int id, String itemName, String description, String unitPrice, int quantity) {
        Id = id;
        this.itemName = itemName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
