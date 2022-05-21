package bo.custom.impl;

import bo.custom.ItemFormBO;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dto.ItemDTO;
import entity.Item;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemFormBOImpl implements ItemFormBO {
    private ItemDao itemDao = (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.Item);

    @Override
    public boolean saveItem(DataSource dataSource, ItemDTO dto) throws SQLException {
        return itemDao.save(new Item(dto.getItemCode(), dto.getItemDescription(), dto.getItemQty(), dto.getItemBuyingPrice(), dto.getItemUnitPrice(), dto.getItemDiscount()), dataSource);
    }

    @Override
    public boolean updateItem(DataSource dataSource, ItemDTO dto) throws SQLException {
        return itemDao.update(new Item(dto.getItemCode(), dto.getItemDescription(), dto.getItemQty(), dto.getItemBuyingPrice(), dto.getItemUnitPrice(), dto.getItemDiscount()), dataSource);
    }

    @Override
    public boolean deleteItem(DataSource dataSource, String itemCode) throws SQLException {
        return itemDao.delete(itemCode, dataSource);
    }

    @Override
    public String generateItemCode(DataSource dataSource) throws SQLException {
        return itemDao.generateItemCode(dataSource);
    }

    @Override
    public boolean checkIfItemExists(DataSource dataSource, String itemCode) throws SQLException {
        return itemDao.checkIfItemExists(dataSource, itemCode);
    }

    @Override
    public ArrayList<ItemDTO> getAll(DataSource dataSource) throws SQLException {
        ArrayList<Item> all = itemDao.getAll(dataSource);
        ArrayList<ItemDTO> getAll = new ArrayList<>();
        for (Item i : all
        ) {
            getAll.add(new ItemDTO(i.getItemCode(), i.getItemDescription(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice(), i.getItemDiscount()));
        }
        return getAll;
    }

    @Override
    public ItemDTO searchItem(DataSource dataSource, String itemCode) throws SQLException {
        Item i = itemDao.search(itemCode, dataSource);
        return i != null ? new ItemDTO(i.getItemCode(), i.getItemDescription(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice(), i.getItemDiscount()) : null;
    }
}
