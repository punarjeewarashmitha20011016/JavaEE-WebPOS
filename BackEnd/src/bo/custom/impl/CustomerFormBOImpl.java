package bo.custom.impl;

import bo.custom.CustomerFormBO;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dto.CustomerDTO;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

import static dao.DaoFactory.DaoTypes.Customer;

public class CustomerFormBOImpl implements CustomerFormBO {

    private CustomerDao customerDao = (CustomerDao) DaoFactory.getDaoFactory().getDao(Customer);

    @Override

    public boolean saveCustomer(DataSource dataSource, CustomerDTO dto) throws SQLException {
        return customerDao.save(new Customer(dto.getId(), dto.getName(), dto.getContactNo(), dto.getNic(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean updateCustomer(DataSource dataSource, CustomerDTO dto) throws SQLException {
        return customerDao.update(new Customer(dto.getId(), dto.getName(), dto.getContactNo(), dto.getNic(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean deleteCustomer(DataSource dataSource, String customerId) throws SQLException {
        return customerDao.delete(customerId, dataSource);
    }

    @Override
    public ArrayList<CustomerDTO> getAll(DataSource dataSource) throws SQLException {
        ArrayList<Customer> all = customerDao.getAll(dataSource);
        ArrayList<CustomerDTO> getAll = new ArrayList<>();
        for (Customer c : all
        ) {
            getAll.add(new CustomerDTO(c.getId(), c.getName(), c.getContactNo(), c.getNic(), c.getAddress()));
        }
        return getAll;
    }

    @Override
    public CustomerDTO searchCustomer(DataSource dataSource, String customerId) throws SQLException {
        Customer search = customerDao.search(customerId, dataSource);
        return new CustomerDTO(search.getId(),search.getName(),search.getContactNo(),search.getNic(),search.getAddress());
    }

    @Override
    public boolean ifCustomerExists(DataSource dataSource, String customerId) throws SQLException {
        return customerDao.checkIfCustomerExists(dataSource,customerId);
    }

    @Override
    public String generateCustomerId(DataSource dataSource) throws SQLException {
        return customerDao.generateCustomerId(dataSource);
    }
}
