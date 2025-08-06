package com.example.inventory.servlet;

import com.example.inventory.dao.ProductDAO;
import com.example.inventory.model.Product;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/products/*")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private Gson gson;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productDAO.getAllProducts();
            String jsonResponse = gson.toJson(products);
            sendResponse(resp, jsonResponse, HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new ServletException("Database error on GET", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Product newProduct = gson.fromJson(requestBody, Product.class);
            productDAO.addProduct(newProduct);
            sendResponse(resp, "{\"message\": \"Product created\"}", HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            throw new ServletException("Database error on POST", e);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                 sendResponse(resp, "{\"error\": \"Product ID required\"}", HttpServletResponse.SC_BAD_REQUEST);
                 return;
            }
            Long id = Long.parseLong(pathInfo.substring(1));
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Product productToUpdate = gson.fromJson(requestBody, Product.class);
            productToUpdate.setId(id);
            productDAO.updateProduct(productToUpdate);
            sendResponse(resp, "{\"message\": \"Product updated\"}", HttpServletResponse.SC_OK);
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Database error on PUT", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
             if (pathInfo == null || pathInfo.equals("/")) {
                 sendResponse(resp, "{\"error\": \"Product ID required\"}", HttpServletResponse.SC_BAD_REQUEST);
                 return;
            }
            Long id = Long.parseLong(pathInfo.substring(1));
            productDAO.deleteProduct(id);
            sendResponse(resp, "{\"message\": \"Product deleted\"}", HttpServletResponse.SC_OK);
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Database error on DELETE", e);
        }
    }

    private void sendResponse(HttpServletResponse resp, String json, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(statusCode);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}