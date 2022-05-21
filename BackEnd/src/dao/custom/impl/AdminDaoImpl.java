package dao.custom.impl;

import dao.custom.AdminDao;
import entity.Admin;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDaoImpl implements AdminDao {
    @Override
    public boolean save(Admin admin, DataSource dataSource) throws SQLException {
        System.out.println(admin.getName());
        return CrudUtil.getExecuteUpdate(dataSource, "INSERT INTO Admin Values(?,?,?,?,?,?,?)", admin.getId(), admin.getName(), admin.getNic(), admin.getContactNo(), admin.getUsername(), admin.getPassword(), admin.getAddress());
    }

    @Override
    public boolean update(Admin admin, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "update Admin set name=?,nic=?,contactNo=?,username=?,password=?,address=? where id=?", admin.getName(), admin.getNic(), admin.getContactNo(), admin.getUsername(), admin.getPassword(), admin.getAddress(), admin.getId());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "Delete from Admin where id=?", s);
    }

    @Override
    public ArrayList<Admin> getAll(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT * FROM Admin");
        ArrayList<Admin> getAll = new ArrayList<>();
        while (rst.next()) {
            getAll.add(new Admin(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return getAll;
    }

    @Override
    public Admin search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT * from Admin where id=?", s);
        while (rst.next()) {
            return new Admin(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public String generateAdminId(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT id from Admin order by id desc limit 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int temp = Integer.parseInt(id.split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "A-00" + temp;
            } else if (temp <= 99) {
                return "A-0" + temp;
            } else {
                return "A-" + temp;
            }
        } else {
            return "A-001";
        }
    }

    @Override
    public boolean checkIfAdminExists(DataSource dataSource, String id) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select id from Admin where id=?", id);
        if (rst.next()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkIfAdminLoginUsernameAndPassword(DataSource dataSource, String userName, String password) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select username,password from admin where username=? and password=?", userName, password);
        while (rst.next()) {
            return true;
        }
        return false;
    }
}
