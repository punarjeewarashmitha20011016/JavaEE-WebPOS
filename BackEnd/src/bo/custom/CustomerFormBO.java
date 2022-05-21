package bo.custom;

import bo.SuperBo;
import dto.CustomerDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerFormBO extends SuperBo {
    public boolean saveCustomer(DataSource dataSource, CustomerDTO dto) throws SQLException;

    public boolean updateCustomer(DataSource dataSource, CustomerDTO dto) throws SQLException;

    public boolean deleteCustomer(DataSource dataSource, String customerId) throws SQLException;

    public ArrayList<CustomerDTO> getAll(DataSource dataSource) throws SQLException;

    public CustomerDTO searchCustomer(DataSource dataSource, String customerId) throws SQLException;

    public boolean ifCustomerExists(DataSource dataSource, String customerId) throws SQLException;

    public String generateCustomerId(DataSource dataSource) throws SQLException;
}
