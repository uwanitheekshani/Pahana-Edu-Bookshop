package com.example.pahanabillingsystem.service;

import com.example.pahanabillingsystem.model.Item;

import java.util.List;

public interface ItemService {

    void addItem(Item item);
    void updateItem(Item item);
    void deleteItem(int itemId);
    Item getItemById(int itemId);
    List<Item> getAllItems();
}
