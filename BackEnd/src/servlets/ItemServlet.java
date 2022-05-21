package servlets;

import bo.BoFactory;
import bo.custom.ItemFormBO;
import dto.ItemDTO;

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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    private ItemFormBO itemFormBO = (ItemFormBO) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.ItemForm);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemDescription = jsonObject.getString("itemDescription");
        int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
        double itemBuyingPrice = Double.parseDouble(jsonObject.getString("itemBuyingPrice"));
        double itemUnitPrice = Double.parseDouble(jsonObject.getString("itemUnitPrice"));
        double itemDiscount = Double.parseDouble(jsonObject.getString("itemDiscount"));
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (itemFormBO.saveItem(dataSource, new ItemDTO(itemCode, itemDescription, itemQty, itemBuyingPrice, itemUnitPrice, itemDiscount))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item saved successfully");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        switch (option) {
            case "getAll":
                try {
                    ArrayList<ItemDTO> all = itemFormBO.getAll(dataSource);
                    for (ItemDTO i : all
                    ) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("itemCode", i.getItemCode());
                        objectBuilder.add("itemDescription", i.getItemDescription());
                        objectBuilder.add("itemQty", i.getItemQty());
                        objectBuilder.add("itemBuyingPrice", i.getItemBuyingPrice());
                        objectBuilder.add("itemUnitPrice", i.getItemUnitPrice());
                        objectBuilder.add("itemDiscount", i.getItemDiscount());
                        objectBuilder.add("status", 200);
                        objectBuilder.add("message", "Item Details retrieved successfully");
                        objectBuilder.add("data", "");
                        arrayBuilder.add(objectBuilder.build());
                    }
                    writer.print(arrayBuilder.build());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("message", "");
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    writer.print(objectBuilder.build());
                }

                try {
                    dataSource.getConnection().close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "searchItem":
                String itemCode = req.getParameter("itemCode");
                try {
                    ItemDTO i = itemFormBO.searchItem(dataSource, itemCode);
                    if (i != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("itemCode", i.getItemCode());
                        objectBuilder.add("itemDescription", i.getItemDescription());
                        objectBuilder.add("itemQty", i.getItemQty());
                        objectBuilder.add("itemBuyingPrice", i.getItemBuyingPrice());
                        objectBuilder.add("itemUnitPrice", i.getItemUnitPrice());
                        objectBuilder.add("itemDiscount", i.getItemDiscount());
                        objectBuilder.add("status", 200);
                        objectBuilder.add("message", "Item Details retrieved successfully");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("message", "Invalid itemcode");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("message", "Error");
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    writer.print(objectBuilder.build());
                }
                try {
                    dataSource.getConnection().close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "ifItemExists":
                String itemCode1 = req.getParameter("itemCode");
                boolean exists = false;
                try {
                    exists = itemFormBO.checkIfItemExists(dataSource, itemCode1);
                    if (exists) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("message", "Item exists");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 400);
                        objectBuilder.add("message", "Item doesn't exists");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("message", "Error");
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    writer.print(objectBuilder.build());
                }
                break;
            case "generateItemCode":
                try {
                    String itemCodeGenerated = itemFormBO.generateItemCode(dataSource);
                    if (itemCodeGenerated != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("message", "Itemcode generated successfully");
                        objectBuilder.add("data", "");
                        objectBuilder.add("itemCode", itemCodeGenerated);
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("message", "item code isn't generated");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("message", "Error");
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    writer.print(objectBuilder.build());
                }

                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemDescription = jsonObject.getString("itemDescription");
        int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
        double itemBuyingPrice = Double.parseDouble(jsonObject.getString("itemBuyingPrice"));
        double itemUnitPrice = Double.parseDouble(jsonObject.getString("itemUnitPrice"));
        double itemDiscount = Double.parseDouble(jsonObject.getString("itemDiscount"));
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (itemFormBO.updateItem(dataSource, new ItemDTO(itemCode, itemDescription, itemQty, itemBuyingPrice, itemUnitPrice, itemDiscount))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Updated Successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "item details for updating is not valid");
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
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String itemCode = req.getParameter("itemCode");
        PrintWriter writer = resp.getWriter();
        try {
            if (itemFormBO.deleteItem(dataSource, itemCode)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Deleted Successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item code is invalid");
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


