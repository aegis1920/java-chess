package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chess.util.JDBCConnector;

public class JdbcTemplate {

    public void executeUpdate(String query, Object... parameters) {
        executeUpdate(query, createPreparedStatementSetter(parameters));
    }

    public void executeUpdate(String query, PreparedStatementSetter pss) {
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            pss.setParameters(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void executeBatch(String query, PreparedStatementSetter pss) {
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            pss.setParameters(pstmt);
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T executeQuery(String query, RowMapper<T> rm, Object... parameters) {
        return executeQuery(query, rm, createPreparedStatementSetter(parameters));
    }

    public <T> T executeQuery(String query, RowMapper<T> rm, PreparedStatementSetter pss) {
        List<T> list = executeQueryOfList(query, rm, pss);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T> List<T> executeQueryOfList(String query, RowMapper<T> rm, Object... parameters) {
        return executeQueryOfList(query, rm, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> executeQueryOfList(String query, RowMapper<T> rm, PreparedStatementSetter pss) {
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            pss.setParameters(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<T> list = new ArrayList<>();
                while(rs.next()) {
                    list.add(rm.mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private PreparedStatementSetter createPreparedStatementSetter(final Object[] parameters) {
        return new PreparedStatementSetter() {
                @Override
                public void setParameters(final PreparedStatement pstmt) throws SQLException {
                    for (int i = 0; i < parameters.length; i++) {
                        pstmt.setObject(i + 1, parameters[i]);
                    }
                }
            };
    }
}
