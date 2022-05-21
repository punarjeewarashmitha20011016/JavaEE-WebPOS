package servlets;

import bo.BoFactory;
import bo.custom.CustomerFormBO;
import dto.CustomerDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    private CustomerFormBO customerFormBO = (CustomerFormBO) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.CustomerForm);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        switch (option) {
            case "ifCustomerExists":
                String customerId = req.getParameter("customerId");
                try {
                    boolean exists = customerFormBO.ifCustomerExists(dataSource, customerId);
                    if (exists) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer exists");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer doesnt exists");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    writer.print(objectBuilder.build());
                }
                break;
            case "searchCustomer":
                String customerId1 = req.getParameter("customerId");
                CustomerDTO customerDTO = null;
                try {
                    customerDTO = customerFormBO.searchCustomer(dataSource, customerId1);
                    if (customerDTO != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("id", customerDTO.getId());
                        objectBuilder.add("name", customerDTO.getName());
                        objectBuilder.add("contactNo", customerDTO.getContactNo());
                        objectBuilder.add("nic", customerDTO.getNic());
                        objectBuilder.add("address", customerDTO.getAddress());

                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer searched successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer searched unsuccessful");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    writer.print(objectBuilder.build());
                }
                break;
            case "getAll":
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                try {
                    ArrayList<CustomerDTO> all = customerFormBO.getAll(dataSource);
                    if (all != null) {
                        for (CustomerDTO dto : all) {
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("id", dto.getId());
                            objectBuilder.add("name", dto.getName());
                            objectBuilder.add("contactNo", dto.getContactNo());
                            objectBuilder.add("nic", dto.getNic());
                            objectBuilder.add("address", dto.getAddress());
                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "customer details retrieved successfully");
                            arrayBuilder.add(objectBuilder.build());
                            writer.print(arrayBuilder.build());
                        }
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "customer details retrieved unsuccessfully");
                        arrayBuilder.add(objectBuilder.build());
                        writer.print(arrayBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    arrayBuilder.add(objectBuilder.build());
                    writer.print(arrayBuilder.build());
                }
                break;
            case "generateCustomerId":
                try {
                    String id = customerFormBO.generateCustomerId(dataSource);
                    if (id != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("customerId", id);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer id generated successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer id generated successfully");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    writer.print(objectBuilder.build());
                }
                break;
        }
        try {
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        int contactNo = Integer.parseInt(jsonObject.getString("contactNo"));
        String nic = jsonObject.getString("nic");
        String address = jsonObject.getString("address");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (customerFormBO.saveCustomer(dataSource, new CustomerDTO(id, name, contactNo, nic, address))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Details are not valid");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        int contactNo = Integer.parseInt(jsonObject.getString("contactNo"));
        String nic = jsonObject.getString("nic");
        String address = jsonObject.getString("address");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            if (customerFormBO.updateCustomer(dataSource, new CustomerDTO(id, name, contactNo, nic, address))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer updated successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer updated unsuccessful");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (customerFormBO.deleteCustomer(dataSource, customerId)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer deleted successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer deleted unsuccessful");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
        }
    }
}
