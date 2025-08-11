package com.example.pahanabillingsystem.servlet;

import com.example.pahanabillingsystem.model.Item;
import com.example.pahanabillingsystem.service.ItemService;
import com.example.pahanabillingsystem.service.impl.ItemServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {

    private final ItemService itemService = new ItemServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createItem(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateItem(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteItem(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            getAllItems(response);
        } else if (pathInfo.contains("byId")) {
            getItemById(request, response);
        } else {
            sendErrorResponse(response, "Invalid path for GET request.");
        }
    }

    /* --------------------------- CRUD Methods --------------------------- */

    private void createItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String unitPrice = request.getParameter("unitPrice");
        String quantityParam = request.getParameter("quantity");

        if (itemName == null || unitPrice == null || quantityParam == null) {
            sendErrorResponse(response, "Missing required fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityParam);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid quantity format.");
            return;
        }

        Item item = new Item(0, itemName, description, unitPrice, quantity);
        itemService.addItem(item);

        sendSuccessResponse(response, "Item added successfully.");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemIdParam = request.getParameter("itemId");

        if (itemIdParam == null || itemIdParam.isEmpty()) {
            sendErrorResponse(response, "Item ID is missing.");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid itemId format.");
            return;
        }

        Item existingItem = itemService.getItemById(itemId);
        if (existingItem != null) {
            existingItem.setItemName(request.getParameter("itemName"));
            existingItem.setDescription(request.getParameter("description"));
            existingItem.setUnitPrice(request.getParameter("unitPrice"));

            String quantityParam = request.getParameter("quantity");
            if (quantityParam != null) {
                try {
                    existingItem.setQuantity(Integer.parseInt(quantityParam));
                } catch (NumberFormatException e) {
                    sendErrorResponse(response, "Invalid quantity format.");
                    return;
                }
            }

            itemService.updateItem(existingItem);
            sendSuccessResponse(response, "Item updated successfully.");
        } else {
            sendErrorResponse(response, "Item not found.");
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemIdParam = request.getParameter("itemId");

        if (itemIdParam == null) {
            sendErrorResponse(response, "Item ID is missing.");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid itemId format.");
            return;
        }

        Item item = itemService.getItemById(itemId);
        if (item != null) {
            itemService.deleteItem(itemId);
            sendSuccessResponse(response, "Item deleted successfully.");
        } else {
            sendErrorResponse(response, "Item not found.");
        }
    }

    private void getAllItems(HttpServletResponse response) throws IOException {
        List<Item> items = itemService.getAllItems();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(items));
    }

    private void getItemById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("itemId");
        if (idParam == null) {
            sendErrorResponse(response, "Missing parameter: itemId");
            return;
        }

        try {
            int itemId = Integer.parseInt(idParam);
            Item item = itemService.getItemById(itemId);
            if (item != null) {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(item));
            } else {
                sendErrorResponse(response, "Item not found.");
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid itemId format.");
        }
    }

    /* --------------------------- Helper Methods --------------------------- */

    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    private static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
