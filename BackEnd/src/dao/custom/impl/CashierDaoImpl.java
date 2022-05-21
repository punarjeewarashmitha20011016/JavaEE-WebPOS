package dao.custom.impl;

import dao.custom.CashierDao;
import entity.Cashier;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierDaoImpl implements CashierDao {
    @Override
    public boolean save(Cashier cashier, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "insert into cashier values(?,?,?,?,?,?,?)", cashier.getId(), cashier.getName(), cashier.getNic(), cashier.getContactNo(), cashier.getUsername(), cashier.getPassword(), cashier.getAddress());
    }

    @Override
    public boolean update(Cashier cashier, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "update cashier set name=?,nic=?,contactNo=?,username=?,password=?,address=? where id=?", cashier.getName(), cashier.getNic(), cashier.getContactNo(), cashier.getUsername(), cashier.getPassword(), cashier.getAddress(), cashier.getId());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "delete from cashier where id=?", s);
    }

    @Override
    public ArrayList<Cashier> getAll(DataSource dataSource) throws SQLException {
        ArrayList<Cashier> getAll = new ArrayList<>();
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from cashier");
        while (rst.next()) {
            getAll.add(new Cashier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return getAll;
    }

    @Override
    public Cashier search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from cashier where id=?", s);
        if (rst.next()) {
            return new Cashier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public String generateCashierId(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select id from cashier order by id desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "C-00" + temp;
            } else if (temp <= 99) {
                return "C-0" + temp;
            } else {
                return "C-" + temp;
            }
        } else {
            return "C-001";
        }
    }

    @Override
    public boolean checkIfCashierExists(DataSource dataSource, String id) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select id from cashier where id=?", id);
        if (rst.next()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIfCashierLoginUsernameAndPassword(DataSource dataSource, String userName, String password) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select username,password from cashier where username=? and password=?", userName, password);
        if (rst.next()){
            return true;
        }
        return false;
    }
}
