package dao.custom.impl;

import dao.custom.CustomerDao;
import entity.Customer;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer customer, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "insert into customer values(?,?,?,?,?)", customer.getId(), customer.getName(), customer.getContactNo(), customer.getNic(), customer.getAddress());
    }

    @Override
    public boolean update(Customer customer, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "update customer set name=?,contactNo=?,nic=?,address=? where id=?", customer.getName(), customer.getContactNo(), customer.getNic(), customer.getAddress(), customer.getId());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "delete from customer where id=?", s);
    }

    @Override
    public ArrayList<Customer> getAll(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from customer");
        ArrayList<Customer> getAll = new ArrayList<>();
        while (rst.next()) {
            getAll.add(new Customer(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getString(4), rst.getString(5)));
        }
        return getAll;
    }

    @Override
    public Customer search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from customer where id=?", s);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getString(4), rst.getString(5));
        }
        return null;
    }

    @Override
    public String generateCustomerId(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select id from customer order by id desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "CU-00" + temp;
            } else if (temp <= 99) {
                return "CU-0" + temp;
            } else {
                return "CU-" + temp;
            }
        } else {
            return "CU-001";
        }
    }

    @Override
    public boolean checkIfCustomerExists(DataSource dataSource, String customerId) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select id from customer where id=?", customerId);
        if (rst.next()) {
            return true;
        }
        return false;
    }
}
