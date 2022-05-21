package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import entity.OrderDetails;
import util.CrudUtil;

import javax.sql.DataSource;
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
        return null;
    }

    @Override
    public OrderDetails search(String s, DataSource dataSource) throws SQLException {
        return null;
    }
}
