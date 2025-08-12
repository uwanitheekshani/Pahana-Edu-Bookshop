package com.example.pahanabillingsystem.servlet;

import com.example.pahanabillingsystem.dto.BillDTO;
import com.example.pahanabillingsystem.model.Bill;
import com.example.pahanabillingsystem.model.Item;
import com.example.pahanabillingsystem.service.BillService;
import com.example.pahanabillingsystem.service.impl.BillServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/bill")
public class BillingServlet extends HttpServlet {

    private final BillService billService = new BillServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        createBill(request, response);
    }

    private void createBill(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read JSON request body
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Gson with LocalDateTime support
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new com.google.gson.JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT,
                                                     com.google.gson.JsonDeserializationContext context) {
                        return LocalDateTime.parse(json.getAsString());
                    }
                })
                .create();

        BillDTO billRequest = gson.fromJson(sb.toString(), BillDTO.class);

        // Convert items list to "id:qty,id:qty" string format
        String itemsString = billRequest.getItems().stream()
                .map(item -> item.getId() + ":" + item.getQuantity())
                .collect(Collectors.joining(","));

        Bill bill = new Bill(
                0,
                billRequest.getCustomerId(),
                itemsString,
                billRequest.getTotalQty(),
                billRequest.getTotalAmount(),
                billRequest.getBillDate()  // You need to add billDate getter/setter in your DTO
        );

        boolean success = billService.createBill(bill, billRequest.getItems());

        if (success) {
            sendSuccessResponse(response, "Bill created successfully.");
        } else {
            sendErrorResponse(response, "Failed to create bill.");
        }
    }

    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    private static class ResponseMessage {
        private final String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}