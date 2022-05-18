package dao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao<T, Id> extends SuperDao {
    public boolean save(T t, DataSource dataSource) throws SQLException;

    public boolean update(T t, DataSource dataSource) throws SQLException;

    public boolean delete(Id id, DataSource dataSource) throws SQLException;

    public ArrayList<T> getAll(DataSource dataSource) throws SQLException;

    public T search(Id id, DataSource dataSource) throws SQLException;
}
