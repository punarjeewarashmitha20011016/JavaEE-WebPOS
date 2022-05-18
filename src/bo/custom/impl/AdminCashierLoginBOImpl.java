package bo.custom.impl;

import bo.custom.AdminCashierLoginBO;
import dao.DaoFactory;
import dao.custom.AdminDao;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AdminCashierLoginBOImpl implements AdminCashierLoginBO {
    private AdminDao adminDao = (AdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Admin);

    @Override
    public boolean ifAdminExists(DataSource dataSource, String userName, String password) throws SQLException {
        return adminDao.checkIfAdminLoginUsernameAndPassword(dataSource, userName, password);
    }

    @Override
    public boolean ifCashierExists(DataSource dataSource, String id) {
        return false;
    }
}
