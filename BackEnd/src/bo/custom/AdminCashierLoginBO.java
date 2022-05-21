package bo.custom;

import bo.SuperBo;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface AdminCashierLoginBO extends SuperBo {
    public boolean ifAdminExists(DataSource dataSource, String userName,String password) throws SQLException;

    public boolean ifCashierExists(DataSource dataSource, String userName,String password) throws SQLException;
}
