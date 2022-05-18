package util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static PreparedStatement getPreparedStatement(DataSource dataSource, String sql, Object... args) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }
        return pstm;
    }

    public static boolean getExecuteUpdate(DataSource dataSource, String sql, Object... args) throws SQLException {
        return getPreparedStatement(dataSource, sql, args).executeUpdate() > 0;
    }

    public static ResultSet getExecuteQuery(DataSource dataSource, String sql, Object... args) throws SQLException {
        return getPreparedStatement(dataSource, sql, args).executeQuery();
    }
}
