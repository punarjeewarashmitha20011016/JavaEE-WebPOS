package servlets;

import bo.BoFactory;
import bo.custom.AdminCashierLoginBO;
import bo.custom.AdminFormSignUpBo;
import dto.AdminDTO;

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

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    private AdminFormSignUpBo adminBo = (AdminFormSignUpBo) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.AdminFormSignUp);
    private AdminCashierLoginBO adminCashierLoginBO = (AdminCashierLoginBO) BoFactory.getBoFactory().getBoTypes(BoFactory.BoTypes.AdminCashierLogin);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("mek wd1");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String nic = jsonObject.getString("nic");
        String contactNo = jsonObject.getString("contactNo");
        String username = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String address = jsonObject.getString("address");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            if (adminBo.saveAdmin(new AdminDTO(id, name, nic, Integer.parseInt(contactNo), username, password, address), dataSource)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Added");
                writer.print(objectBuilder.build());
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Data inserted is not valid");
                writer.print(objectBuilder.build());
            }
            dataSource.getConnection().close();
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
        System.out.println("Get method invoked");
        String option = req.getParameter("option");
        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        switch (option) {
            case "getAll":
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                ArrayList<AdminDTO> adminDetails = null;
                try {
                    adminDetails = adminBo.getAdminDetails(dataSource);
                    if (adminDetails != null) {
                        for (int i = 0; i < adminDetails.size(); i++) {
                            String id = adminDetails.get(i).getId();
                            String name = adminDetails.get(i).getName();
                            String nic = adminDetails.get(i).getNic();
                            int contactNo = adminDetails.get(i).getContactNo();
                            String userName = adminDetails.get(i).getUsername();
                            String password = adminDetails.get(i).getPassword();
                            String address = adminDetails.get(i).getAddress();
                            objectBuilder.add("adminId", id);
                            objectBuilder.add("adminName", name);
                            objectBuilder.add("adminNic", nic);
                            objectBuilder.add("adminContactNo", contactNo);
                            objectBuilder.add("adminUserName", userName);
                            objectBuilder.add("adminPassword", password);
                            objectBuilder.add("adminAddress", address);
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Admin Details retrieved successfully");
                            JsonObject build = objectBuilder.build();
                            arrayBuilder.add(build);
                        }
                        writer.print(arrayBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Admin Details retrieved successfully");
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
            case "generateAdminId":
                try {
                    String id = adminBo.generateAdminId(dataSource);
                    System.out.println(id);
                    if (id != null) {
                        System.out.println("generate admin id");
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("adminID", id);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Admin Id generated successfully");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Admin id is null");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    resp.setStatus(200);
                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                    objectBuilder.add("status", 500);
                    objectBuilder.add("data", throwables.getLocalizedMessage());
                    objectBuilder.add("message", "Error");
                    JsonObject build = objectBuilder.build();
                    writer.print(build);
                }
                break;
            case "searchAdmin":
                String adminId = req.getParameter("adminId");
                AdminDTO adminDTO = null;
                try {
                    adminDTO = adminBo.searchAdmin(adminId, dataSource);
                    if (adminDTO != null) {
                        System.out.println(adminDTO.getId());
                        objectBuilder.add("adminId", adminDTO.getId());
                        objectBuilder.add("adminName", adminDTO.getName());
                        objectBuilder.add("adminNic", adminDTO.getNic());
                        objectBuilder.add("adminContactNo", adminDTO.getContactNo());
                        objectBuilder.add("adminUserName", adminDTO.getUsername());
                        objectBuilder.add("adminPassword", adminDTO.getPassword());
                        objectBuilder.add("adminAddress", adminDTO.getAddress());

                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Admin searched successfully");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);

                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Admin id is not valid");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
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
                break;
            case "checkIfAdminExists":
                String adminID = req.getParameter("adminID");
                try {
                    boolean exists = adminBo.checkIfAdminExists(dataSource, adminID);
                    if (exists == true) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Successfully Checked if admin exists or not");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
                    } else {
                        objectBuilder.add("bool", exists);
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Invalid Input data");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
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
                break;
            case "loginCheck":
                System.out.println("Login Check GET method");
                String userName = req.getParameter("adminUserName");
                String password = req.getParameter("adminPassword");
                System.out.println("Admin Username = " + userName + " - - Admin Password = " + password);

                try {
                    boolean exists = adminCashierLoginBO.ifAdminExists(dataSource, userName, password);
                    System.out.println("Login check = " + exists);
                    if (exists) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Login Successful");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Login Credentials are not valid");
                        JsonObject build = objectBuilder.build();
                        writer.print(build);
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
                break;
            default:
                System.out.println("Mukuth wd nh");
                break;
        }
        try {
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String nic = jsonObject.getString("nic");
        String contactNo = jsonObject.getString("contactNo");
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String address = jsonObject.getString("address");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (adminBo.updateAdmin(new AdminDTO(id, name, nic, Integer.parseInt(contactNo), userName, password, address), dataSource)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Updated");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Input data is invalid");
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
        /*try {
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminID = req.getParameter("adminID");
        System.out.println(adminID + "admin Id in delete method");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (adminBo.deleteAdmin(adminID, dataSource)) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                JsonObject build = objectBuilder.build();
                writer.print(build);
                dataSource.getConnection().close();
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Invalid Id");
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
}
