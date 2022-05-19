package dao.custom.impl;

import dao.custom.OrderDao;
import entity.Order;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(Order order, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "INSERT INTO `ORDER` values (?,?,?,?,?,?)", order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getOrderTime(), order.getDiscount(), order.getTotalAmount());
    }

    @Override
    public boolean update(Order order, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "UPDATE `Order` SET customerId=?,orderDate=?,orderTime=?,discount=?,totalAmount=? where orderId=?", order.getCustomerId(), order.getOrderDate(), order.getOrderTime(), order.getDiscount(), order.getTotalAmount(), order.getOrderId());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT * FROM `order`");
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(rst.getString(1), rst.getString(2), rst.getDate(3), rst.getString(4), rst.getDouble(5), rst.getDouble(6)));
        }
        return orders;
    }


    @Override
    public Order search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT * FROM `ORDER` WHERE orderId=?", s);
        if (rst.next()) {
            return new Order(rst.getString(1), rst.getString(2), rst.getDate(3), rst.getString(4), rst.getDouble(5), rst.getDouble(6));
        }
        return null;
    }

    @Override
    public String generateOrderId(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT orderId from `order` order by orderId desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "O-00" + temp;
            } else if (temp <= 99) {
                return "O-0" + temp;
            } else {
                return "O-" + temp;
            }
        } else {
            return "O-001";
        }
    }

    @Override
    public boolean ifOrderExists(DataSource dataSource, String id) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT orderId from `order` where orderId=?", id);
        if (rst.next()){
            return true;
        }
        return false;
    }
}
