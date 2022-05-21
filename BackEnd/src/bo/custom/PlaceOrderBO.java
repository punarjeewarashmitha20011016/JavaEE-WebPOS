package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;
import dto.OrderDetailsDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBo {
    public boolean purchaseOrder(DataSource dataSource, OrderDTO dto) throws SQLException;

    public boolean updateOrder(DataSource dataSource, OrderDTO dto);

    public String generateOrderId(DataSource dataSource) throws SQLException;

    public ArrayList<OrderDTO> getAllOrders(DataSource dataSource) throws SQLException;

    public OrderDTO searchOrder(DataSource dataSource, String orderId) throws SQLException;

    public OrderDetailsDTO searchOrderDetails(DataSource dataSource, String orderId) throws SQLException;

    public ArrayList<OrderDetailsDTO> getAllOrderDetails(DataSource dataSource) throws SQLException;
}
