package com.example.pahanabillingsystem.servlet;

import com.example.pahanabillingsystem.model.Customer;
import com.example.pahanabillingsystem.service.CustomerService;
import com.example.pahanabillingsystem.service.UserService;
import com.example.pahanabillingsystem.service.impl.CustomerServiceImpl;
import com.example.pahanabillingsystem.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createCustomer(request, response);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateCustomer(request, response);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteCustomer(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all cars
            getAllCustomer(response);
        } else if (pathInfo.contains("byId")) {
            // Get images for a specific car
            getCustomerById(request, response);
        } else {
            sendErrorResponse(response, "Invalid path for GET request.");
        }
    }


         /*----------------------------------------------------------------------*/
    private void createCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");


        Customer customer = new Customer(0, accountNumber, name, address, telephone);

        // Call service to add car and populate the carDTO with the ID
        customerService.addCustomer(customer);
        int customerId = customer.getId();  // Get the ID of the newly added car


        sendSuccessResponse(response, "Car and Image added successfully.");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdParam = request.getParameter("customerId");


        if (customerIdParam == null || customerIdParam.isEmpty()) {
            sendErrorResponse(response, "Customer ID is missing.");
            return;
        }

        int customerId = Integer.parseInt(customerIdParam);

        Customer customer = customerService.getCustomerById(customerId);

        if (customer != null) {
            // Update car details
            customer.setAccountNumber(request.getParameter("accountNumber"));
            customer.setName(request.getParameter("name"));
            customer.setAddress(request.getParameter("address"));
            customer.setTelephone(request.getParameter("telephone"));



            // Call service to update car
            customerService.updateCustomer(customer);

            sendSuccessResponse(response, "Car updated successfully.");
        } else {
            sendErrorResponse(response, "Car not found.");
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer customer = customerService.getCustomerById(customerId);

        if (customer != null) {
            customerService.deleteCustomer(customerId);
            sendSuccessResponse(response, "Customer  deleted successfully.");
        } else {
            sendErrorResponse(response, "Customer not found.");
        }
    }
    private void getAllCustomer(HttpServletResponse response) throws IOException {
        List<Customer> customers = customerService.getAllCustomers();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(customers));
    }

    private void getCustomerById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("customerId");
        if (idParam == null) {
            sendErrorResponse(response, "Missing parameter: customerId");
            return;
        }

        try {
            int customerId = Integer.parseInt(idParam);
            Customer customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(customer));
            } else {
                sendErrorResponse(response, "Customer not found.");
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid customerId format.");
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
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
