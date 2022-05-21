package dao.custom;

import dao.CrudDao;
import entity.Admin;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface AdminDao extends CrudDao<Admin, String> {
    public String generateAdminId(DataSource dataSource) throws SQLException;

    public boolean checkIfAdminExists(DataSource dataSource, String id) throws SQLException;

    public boolean checkIfAdminLoginUsernameAndPassword(DataSource dataSource,String userName,String password) throws SQLException;
}
