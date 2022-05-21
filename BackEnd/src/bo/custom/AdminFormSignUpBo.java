package bo.custom;

import bo.SuperBo;
import dto.AdminDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminFormSignUpBo extends SuperBo {
    public boolean saveAdmin(AdminDTO dto, DataSource dataSource) throws SQLException;

    public boolean updateAdmin(AdminDTO dto, DataSource dataSource) throws SQLException;

    public boolean deleteAdmin(String id, DataSource dataSource) throws SQLException;

    public ArrayList<AdminDTO> getAdminDetails(DataSource dataSource) throws SQLException;

    public AdminDTO searchAdmin(String id, DataSource dataSource) throws SQLException;

    public String generateAdminId(DataSource dataSource) throws SQLException;

    public boolean checkIfAdminExists(DataSource dataSource,String id) throws SQLException;
}
