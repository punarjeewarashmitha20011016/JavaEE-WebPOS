package bo.custom;

import bo.SuperBo;
import dto.CashierDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CashierFormSignUpBO extends SuperBo {
    public boolean saveCashier(DataSource dataSource, CashierDTO cashierDTO) throws SQLException;

    public boolean updateCashier(DataSource dataSource, CashierDTO cashierDTO) throws SQLException;

    public boolean deleteCashier(DataSource dataSource, String id) throws SQLException;

    public ArrayList<CashierDTO> getCashierDetails(DataSource dataSource) throws SQLException;

    public CashierDTO searchCashier(String id, DataSource dataSource) throws SQLException;

    public String generateCashierId(DataSource dataSource) throws SQLException;

    public boolean checkIfCashierExists(DataSource dataSource, String id) throws SQLException;
}
