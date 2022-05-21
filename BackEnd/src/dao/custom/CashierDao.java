package dao.custom;

import dao.CrudDao;
import entity.Cashier;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface CashierDao extends CrudDao<Cashier, String> {
    public String generateCashierId(DataSource dataSource) throws SQLException;

    public boolean checkIfCashierExists(DataSource dataSource, String id) throws SQLException;

    public boolean checkIfCashierLoginUsernameAndPassword(DataSource dataSource, String userName, String password) throws SQLException;
}
