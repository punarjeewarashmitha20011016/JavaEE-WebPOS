package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import entity.OrderDetails;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean save(OrderDetails od, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "insert into orderdetails values(?,?,?,?,?,?,?)", od.getOrderId(), od.getItemCode(), od.getItemDescription(), od.getItemQty(), od.getItemPrice(), od.getItemDiscount(), od.getTotal());
    }

    @Override
    public boolean update(OrderDetails orderDetails, DataSource dataSource) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "SELECT * FROM `orderdetails`");
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        while (rst.next()) {
            orderDetails.add(new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getDouble(5), rst.getDouble(6), rst.getDouble(7)));
        }
        return orderDetails;
    }

    @Override
    public OrderDetails search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from orderdetails where orderId=?", s);
        if (rst.next()){
            return new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getDouble(5), rst.getDouble(6), rst.getDouble(7));
        }
        return null;
    }
}
