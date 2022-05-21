package dao.custom;

import dao.CrudDao;
import entity.Order;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order, String> {
    public String generateOrderId(DataSource dataSource) throws SQLException;

    public boolean ifOrderExists(DataSource dataSource, String id) throws SQLException;
}
