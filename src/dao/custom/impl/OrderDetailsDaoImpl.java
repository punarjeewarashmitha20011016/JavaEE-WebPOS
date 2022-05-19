package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import entity.OrderDetails;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean save(OrderDetails orderDetails, DataSource dataSource) throws SQLException {
        return false;
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
