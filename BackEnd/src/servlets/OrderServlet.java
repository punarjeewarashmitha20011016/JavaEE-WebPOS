package servlets;

import bo.BoFactory;
import bo.custom.PlaceOrderBO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;

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
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.PlaceOrder);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String orderId = jsonObject.getString("orderId");
        String customerId = jsonObject.getString("customerId");
        LocalDate orderDate = LocalDate.parse(jsonObject.getString("orderDate"));
        String orderTime = jsonObject.getString("orderTime");
        double orderDiscount = Double.parseDouble(jsonObject.getString("orderDiscount"));
        double orderTotal = Double.parseDouble(jsonObject.getString("orderTotal"));
        JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");
        ArrayList<OrderDetailsDTO> getOrderDetails = new ArrayList<>();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        for (int i = 0; i < orderDetails.size(); i++) {
            getOrderDetails.add(new OrderDetailsDTO(orderDetails.getString(i, "orderId"), orderDetails.getString(i, "itemCode"), orderDetails.getString(i, "itemDescription"), Integer.parseInt(orderDetails.getString(i, "itemQty")), Double.parseDouble(orderDetails.getString(i, "itemPrice")), Double.parseDouble(orderDetails.getString(i, "itemDiscount")), Double.parseDouble(orderDetails.getString(i, "itemTotal"))));
        }

        try {
            if (placeOrderBO.purchaseOrder(dataSource, new OrderDTO(orderId, customerId, orderDate, orderTime, orderDiscount, orderTotal, getOrderDetails))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Order added successfully");
                writer.print(objectBuilder.build());
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Order adding is unsuccessful");
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
        try {
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        PrintWriter writer = resp.getWriter();
        switch (option) {
            case "generateOrderId":
                try {
                    String id = placeOrderBO.generateOrderId(dataSource);
                    if (id != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Order ID generated successfully");
                        objectBuilder.add("orderId", id);
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Order ID generated unsuccessful");
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
                try {
                    ArrayList<OrderDTO> allOrders = placeOrderBO.getAllOrders(dataSource);
                    if (allOrders != null) {
                        for (OrderDTO dto : allOrders
                        ) {
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("orderId", dto.getOrderId());
                            objectBuilder.add("customerId", dto.getCustomerId());
                            objectBuilder.add("orderDate", String.valueOf(dto.getOrderDate()));
                            objectBuilder.add("orderTime", dto.getOrderTime());
                            objectBuilder.add("discount", dto.getDiscount());
                            objectBuilder.add("total", dto.getTotalAmount());

                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Order details generated successfully");

                            arrayBuilder.add(objectBuilder.build());
                        }
                        writer.print(arrayBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Order details generated unsuccessful");

                        arrayBuilder.add(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    arrayBuilder.add(objectBuilder.build());
                }
                break;
            case "searchOrder":
                String orderId = req.getParameter("orderId");
                try {
                    OrderDTO orderDTO = placeOrderBO.searchOrder(dataSource, orderId);
                    OrderDetailsDTO orderDetails = placeOrderBO.searchOrderDetails(dataSource, orderId);
                    JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();

                    if ((orderDTO != null) && (orderDetails != null)) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("orderId", orderDTO.getOrderId());
                        objectBuilder.add("customerId", orderDTO.getCustomerId());
                        objectBuilder.add("orderDate", String.valueOf(orderDTO.getOrderDate()));
                        objectBuilder.add("orderTime", orderDTO.getOrderTime());
                        objectBuilder.add("discount", orderDTO.getDiscount());
                        objectBuilder.add("total", orderDTO.getTotalAmount());

                        objectBuilder2.add("orderId", orderDetails.getOrderId());
                        objectBuilder2.add("itemCode", orderDetails.getItemCode());
                        objectBuilder2.add("itemDescription", orderDetails.getItemDescription());
                        objectBuilder2.add("itemQty", orderDetails.getItemQty());
                        objectBuilder2.add("itemPrice", orderDetails.getItemPrice());
                        objectBuilder2.add("itemDiscount", orderDetails.getItemDiscount());
                        objectBuilder2.add("total", orderDetails.getTotal());
                        JsonObject build = objectBuilder2.build();
                        objectBuilder.add("orderDetails", build);

                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Order searched successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Order searched unsuccessful");
                        writer.print(objectBuilder.build());
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", "");
                    objectBuilder.add("message", "Error");
                    writer.print(objectBuilder.build());
                }
                break;
            default:
                System.out.println("Default in Order Servlet");
                break;
        }
        try {
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
