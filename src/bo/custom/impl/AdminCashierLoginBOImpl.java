package bo.custom.impl;

import bo.custom.AdminCashierLoginBO;
import dao.DaoFactory;
import dao.custom.AdminDao;
import dao.custom.CashierDao;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AdminCashierLoginBOImpl implements AdminCashierLoginBO {
    private AdminDao adminDao = (AdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Admin);
    private CashierDao cashierDao = (CashierDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Cashier);

    @Override
    public boolean ifAdminExists(DataSource dataSource, String userName, String password) throws SQLException {
        return adminDao.checkIfAdminLoginUsernameAndPassword(dataSource, userName, password);
    }

    @Override
    public boolean ifCashierExists(DataSource dataSource, String username, String password) throws SQLException {
        return cashierDao.checkIfCashierLoginUsernameAndPassword(dataSource, username, password);
    }
}
