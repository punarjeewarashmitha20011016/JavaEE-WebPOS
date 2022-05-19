package servlets;

import bo.BoFactory;
import bo.custom.CashierFormSignUpBO;
import dto.CashierDTO;

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

@WebServlet(urlPatterns = "/cashier")
public class CashierServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    private CashierFormSignUpBO cashierFormSignUpBO = (CashierFormSignUpBO) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.cashierFormSignUp);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        switch (option) {
            case "generateCashierId":
                try {
                    String id = cashierFormSignUpBO.generateCashierId(dataSource);
                    System.out.println(id + " - cashier Id");
                    if (id != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("cashierId", id);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Generated cashier id successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Id is not valid");
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
                    ArrayList<CashierDTO> cashierDetails = cashierFormSignUpBO.getCashierDetails(dataSource);
                    for (CashierDTO dto : cashierDetails
                    ) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("id", dto.getId());
                        objectBuilder.add("name", dto.getName());
                        objectBuilder.add("nic", dto.getNic());
                        objectBuilder.add("contactNo", dto.getContactNo());
                        objectBuilder.add("userName", dto.getUsername());
                        objectBuilder.add("password", dto.getPassword());
                        objectBuilder.add("address", dto.getAddress());


                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Cashier details generated successfully");
                        arrayBuilder.add(objectBuilder.build());
                    }
                    writer.print(arrayBuilder.build());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    writer.print(objectBuilder.build());
                }
                break;
            case "ifCashierExists":
                String cashierId = req.getParameter("cashierId");
                try {
                    boolean exists = cashierFormSignUpBO.checkIfCashierExists(dataSource, cashierId);
                    if (exists == true) {
                        System.out.println("cashier exists - " + exists);
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "cashier exists");
                        writer.print(objectBuilder.build());
                    } else {
                        System.out.println("cashier does not exists - " + exists);
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "cashier does not exists");
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
            case "searchCashier":
                String cashierID = req.getParameter("cashierId");
                System.out.println("Cashier id in servlet - " + cashierID);
                CashierDTO dto = null;
                try {
                    dto = cashierFormSignUpBO.searchCashier(cashierID, dataSource);
                    if (dto != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                        objectBuilder.add("id", dto.getId());
                        objectBuilder.add("name", dto.getName());
                        objectBuilder.add("nic", dto.getNic());
                        objectBuilder.add("contactNo", dto.getContactNo());
                        objectBuilder.add("userName", dto.getUsername());
                        objectBuilder.add("password", dto.getPassword());
                        objectBuilder.add("address", dto.getAddress());

                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "cashier details searched successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "cashier id is not valid");
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
            default:
                System.out.println("default");
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
        String nic = jsonObject.getString("nic");
        int contactNo = Integer.parseInt(jsonObject.getString("contactNo"));
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String address = jsonObject.getString("address");
        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (cashierFormSignUpBO.saveCashier(dataSource, new CashierDTO(id, name, nic, contactNo, userName, password, address))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Cashier saved Successfully");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "cashier details are not valid");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            JsonObject build = objectBuilder.build();
            writer.print(build);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String nic = jsonObject.getString("nic");
        int contactNo = Integer.parseInt(jsonObject.getString("contactNo"));
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String address = jsonObject.getString("address");
        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (cashierFormSignUpBO.updateCashier(dataSource, new CashierDTO(id, name, nic, contactNo, userName, password, address))) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Cashier updated Successfully");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "cashier details are not valid");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            JsonObject build = objectBuilder.build();
            writer.print(build);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cashierId = req.getParameter("cashierId");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (cashierFormSignUpBO.deleteCashier(dataSource, cashierId)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully deleted");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Cashier id is invalid");
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
