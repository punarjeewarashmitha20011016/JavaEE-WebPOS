package bo.custom.impl;

import bo.custom.PlaceOrderBO;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Item;
import entity.Order;
import entity.OrderDetails;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBoImpl implements PlaceOrderBO {
    private OrderDao orderDao = (OrderDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Order);
    private OrderDetailsDao orderDetailsDao = (OrderDetailsDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.OrderDetails);
    private ItemDao itemDao = (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Item);

    @Override
    public boolean purchaseOrder(DataSource dataSource, OrderDTO dto) throws SQLException {
        boolean exists = orderDao.ifOrderExists(dataSource, dto.getOrderId());
        if (exists) {
            return false;
        }
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Order order = new Order(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate(), dto.getOrderTime(), dto.getDiscount(), dto.getTotalAmount());

        if (!orderDao.save(order, dataSource)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (OrderDetailsDTO d : dto.getOrderDetailsDTO()
        ) {
            OrderDetails orderDetails = new OrderDetails(d.getOrderId(), d.getItemCode(), d.getItemDescription(), d.getItemQty(), d.getItemPrice(), d.getItemDiscount(), d.getTotal());
            if (!orderDetailsDao.save(orderDetails, dataSource)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            Item search = itemDao.search(orderDetails.getOrderId(), dataSource);
            search.setItemQty(search.getItemQty() - orderDetails.getItemQty());
            if (!itemDao.update(search, dataSource)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean updateOrder(DataSource dataSource, OrderDTO dto) {
        return false;
    }

    @Override
    public String generateOrderId(DataSource dataSource) throws SQLException {
        return orderDao.generateOrderId(dataSource);
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders(DataSource dataSource) throws SQLException {
        ArrayList<Order> all = orderDao.getAll(dataSource);
        ArrayList<OrderDTO> getAll = new ArrayList<>();
        for (Order o : all
        ) {
            getAll.add(new OrderDTO(o.getOrderId(), o.getCustomerId(), o.getOrderDate(), o.getOrderTime(), o.getDiscount(), o.getTotalAmount()));
        }
        return getAll;
    }

    @Override
    public OrderDTO searchOrder(DataSource dataSource, String orderId) throws SQLException {
        Order search = orderDao.search(orderId, dataSource);
        return new OrderDTO(search.getOrderId(),search.getCustomerId(),search.getOrderDate(),search.getOrderTime(),search.getDiscount(),search.getTotalAmount());
    }

    @Override
    public OrderDetailsDTO searchOrderDetails(DataSource dataSource, String orderId) throws SQLException {
        OrderDetails search = orderDetailsDao.search(orderId, dataSource);
        return new OrderDetailsDTO(search.getOrderId(),search.getItemCode(),search.getItemDescription(),search.getItemQty(),search.getItemPrice(),search.getItemDiscount(),search.getTotal());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails(DataSource dataSource) throws SQLException {
        return null;
    }
}
