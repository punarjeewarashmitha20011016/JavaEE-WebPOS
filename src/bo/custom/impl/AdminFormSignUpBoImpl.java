package bo.custom.impl;

import bo.custom.AdminFormSignUpBo;
import dao.DaoFactory;
import dao.custom.AdminDao;
import dto.AdminDTO;
import entity.Admin;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFormSignUpBoImpl implements AdminFormSignUpBo {
    private AdminDao adminDao = (AdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Admin);

    @Override
    public boolean saveAdmin(AdminDTO dto, DataSource dataSource) throws SQLException {
        return adminDao.save(new Admin(dto.getId(), dto.getName(), dto.getNic(), dto.getContactNo(), dto.getUsername(), dto.getPassword(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean updateAdmin(AdminDTO dto, DataSource dataSource) throws SQLException {
        return adminDao.update(new Admin(dto.getId(), dto.getName(), dto.getNic(), dto.getContactNo(), dto.getUsername(), dto.getPassword(), dto.getAddress()), dataSource);
    }

    @Override
    public boolean deleteAdmin(String id, DataSource dataSource) throws SQLException {
        return adminDao.delete(id, dataSource);
    }

    @Override
    public ArrayList<AdminDTO> getAdminDetails(DataSource dataSource) throws SQLException {
        ArrayList<Admin> all = adminDao.getAll(dataSource);
        ArrayList<AdminDTO> getAll = new ArrayList<>();
        for (Admin a : all
        ) {
            getAll.add(new AdminDTO(a.getId(), a.getName(), a.getNic(), a.getContactNo(), a.getUsername(), a.getPassword(), a.getAddress()));
        }
        return getAll;
    }

    @Override
    public AdminDTO searchAdmin(String id, DataSource dataSource) throws SQLException {
        Admin search = adminDao.search(id, dataSource);
        return new AdminDTO(search.getId(), search.getName(), search.getNic(), search.getContactNo(), search.getUsername(), search.getPassword(), search.getAddress());
    }

    @Override
    public String generateAdminId(DataSource dataSource) throws SQLException {
        String adminID = adminDao.generateAdminId(dataSource);
        return adminID;
    }

    @Override
    public boolean checkIfAdminExists(DataSource dataSource, String id) throws SQLException {
        return adminDao.checkIfAdminExists(dataSource, id);
    }
}
