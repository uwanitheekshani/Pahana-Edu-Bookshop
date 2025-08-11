package com.example.pahanabillingsystem.service.impl;

import com.example.pahanabillingsystem.dao.ItemDAO;
import com.example.pahanabillingsystem.model.Item;
import com.example.pahanabillingsystem.service.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ItemDAO itemDAO = new ItemDAO();

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void addItem(Item item) {
        Item newItem = new Item();
        newItem.setItemName(item.getItemName());
        newItem.setDescription(item.getDescription());
        newItem.setUnitPrice(item.getUnitPrice());
        newItem.setQuantity(item.getQuantity());
        itemDAO.addItem(newItem);
    }

    @Override
    public void updateItem(Item item) {

        Item updatedItem = new Item();
        updatedItem.setId(item.getId());
        updatedItem.setItemName(item.getItemName());
        updatedItem.setDescription(item.getDescription());
        updatedItem.setUnitPrice(item.getUnitPrice());
        updatedItem.setQuantity(item.getQuantity());
        itemDAO.updateItem(updatedItem);
    }

    @Override
    public void deleteItem(int itemId) {
        itemDAO.deleteItem(itemId);
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = itemDAO.getItemById(itemId);
        return item != null ? item : null;
    }

    @Override
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }
}
