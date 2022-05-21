package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBo {
    public boolean purchaseOrder(DataSource dataSource, OrderDTO dto) throws SQLException;

    public boolean updateOrder(DataSource dataSource, OrderDTO dto);

    public String generateOrderId(DataSource dataSource) throws SQLException;

    public ArrayList<OrderDTO> getAllOrders(DataSource dataSource);

    public OrderDTO searchOrder(DataSource dataSource, String orderId);
}
