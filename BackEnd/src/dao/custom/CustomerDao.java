package dao.custom;

import dao.CrudDao;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface CustomerDao extends CrudDao<Customer, String> {
    public String generateCustomerId(DataSource dataSource) throws SQLException;
    public boolean checkIfCustomerExists(DataSource dataSource,String customerId) throws SQLException;
}
