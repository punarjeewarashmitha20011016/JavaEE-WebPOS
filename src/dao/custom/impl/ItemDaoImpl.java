package dao.custom.impl;

import dao.custom.ItemDao;
import entity.Item;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item item, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "insert into item values(?,?,?,?,?,?)", item.getItemCode(), item.getItemDescription(), item.getItemQty(), item.getItemBuyingPrice(), item.getItemUnitPrice(), item.getItemDiscount());
    }

    @Override
    public boolean update(Item item, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "update item set itemDescription=?,itemQty=?,itemBuyingPrice=?,itemUnitPrice=?,itemDiscount=? where itemCode=?", item.getItemDescription(), item.getItemQty(), item.getItemBuyingPrice(), item.getItemUnitPrice(), item.getItemDiscount(), item.getItemCode());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException {
        return CrudUtil.getExecuteUpdate(dataSource, "delete from item where itemCode=?", s);
    }

    @Override
    public ArrayList<Item> getAll(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from item");
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getDouble(5), rst.getDouble(6)));
        }
        return items;
    }

    @Override
    public Item search(String s, DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select * from item where itemCode=?", s);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getDouble(5), rst.getDouble(6));
        }
        return null;
    }

    @Override
    public String generateItemCode(DataSource dataSource) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select itemCode from item order by itemCode desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "I-00" + temp;
            } else if (temp <= 99) {
                return "I-0" + temp;
            } else {
                return "I-" + temp;
            }
        } else {
            return "I-001";
        }
    }

    @Override
    public boolean checkIfItemExists(DataSource dataSource, String itemCode) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(dataSource, "select itemCode from item where itemCode=?", itemCode);
        if (rst.next()) {
            return true;
        }
        return false;
    }
}
