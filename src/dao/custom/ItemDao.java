package dao.custom;

import dao.CrudDao;
import entity.Item;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface ItemDao extends CrudDao<Item, String> {
    public String generateItemCode(DataSource dataSource) throws SQLException;

    public boolean checkIfItemExists(DataSource dataSource, String itemCode) throws SQLException;
}
