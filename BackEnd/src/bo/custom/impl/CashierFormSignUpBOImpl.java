package bo.custom.impl;

import bo.custom.CashierFormSignUpBO;
import dao.DaoFactory;
import dao.custom.CashierDao;
import dto.CashierDTO;
import entity.Cashier;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierFormSignUpBOImpl implements CashierFormSignUpBO {

    private CashierDao cashierDao = (CashierDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Cashier);

    @Override
    public boolean saveCashier(DataSource dataSource, CashierDTO dto) throws SQLException {
        return cashierDao.save(new Cashier(dto.getId(), dto.getName(), dto.getNic(), dto.getContactNo(), dto.getUsername(), dto.getPassword(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean updateCashier(DataSource dataSource, CashierDTO dto) throws SQLException {
        return cashierDao.update(new Cashier(dto.getId(), dto.getName(), dto.getNic(), dto.getContactNo(), dto.getUsername(), dto.getPassword(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean deleteCashier(DataSource dataSource, String id) throws SQLException {
        return cashierDao.delete(id, dataSource);
    }

    @Override
    public ArrayList<CashierDTO> getCashierDetails(DataSource dataSource) throws SQLException {
        ArrayList<Cashier> all = cashierDao.getAll(dataSource);
        ArrayList<CashierDTO> getAll = new ArrayList<>();
        for (Cashier c : all
        ) {
            getAll.add(new CashierDTO(c.getId(), c.getName(), c.getNic(), c.getContactNo(), c.getUsername(), c.getPassword(), c.getAddress()));
        }
        return getAll;
    }

    @Override
    public CashierDTO searchCashier(String id, DataSource dataSource) throws SQLException {
        Cashier c = cashierDao.search(id, dataSource);
        return c != null ? new CashierDTO(c.getId(), c.getName(), c.getNic(), c.getContactNo(), c.getUsername(), c.getPassword(), c.getAddress()) : null;
    }

    @Override
    public String generateCashierId(DataSource dataSource) throws SQLException {
        return cashierDao.generateCashierId(dataSource);
    }

    @Override
    public boolean checkIfCashierExists(DataSource dataSource, String id) throws SQLException {
        return cashierDao.checkIfCashierExists(dataSource, id);
    }
}
