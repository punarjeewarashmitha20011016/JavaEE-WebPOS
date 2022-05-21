package bo.custom;

import bo.SuperBo;
import dto.ItemDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemFormBO extends SuperBo {
    public boolean saveItem(DataSource dataSource, ItemDTO dto) throws SQLException;

    public boolean updateItem(DataSource dataSource, ItemDTO dto) throws SQLException;

    public boolean deleteItem(DataSource dataSource, String itemCode) throws SQLException;

    public String generateItemCode(DataSource dataSource) throws SQLException;

    public boolean checkIfItemExists(DataSource dataSource, String itemCode) throws SQLException;

    public ArrayList<ItemDTO> getAll(DataSource dataSource) throws SQLException;

    public ItemDTO searchItem(DataSource dataSource, String itemCode) throws SQLException;
}
