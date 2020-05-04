package chess.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import chess.domain.board.Position;
import chess.domain.piece.Blank;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMapper;
import chess.domain.piece.Pieces;

public class GameDAOImpl implements GameDAO {
    private static final GameDAO GAME_DAO = new GameDAOImpl();

    public static GameDAO getInstance() {
        return GAME_DAO;
    }

    @Override
    public void removeAllPiecesById(int roomId) {
        String query = "DELETE FROM board WHERE room_id = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, roomId);
    }

    @Override
    public void addAllPiecesById(int roomId, Pieces pieces) {
        String query = "INSERT INTO board(room_id, piece_name, piece_position, piece_color) VALUES (?, ?, ?, ?)";

        PreparedStatementSetter pss = pstmt -> {
            for (Position position : pieces.getPieces().keySet()) {
                Piece piece = pieces.getPieceByPosition(position);

                pstmt.setInt(1, roomId);
                pstmt.setString(2, piece.getSymbol());
                pstmt.setString(3, position.getPosition());
                pstmt.setString(4, piece.getColor().name());
                pstmt.addBatch();
            }
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeBatch(query, pss);
    }

    @Override
    public void updatePieceByPosition(String currentPosition, String nextPosition) {
        String query = "UPDATE board SET piece_position = ? WHERE piece_position = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, nextPosition, currentPosition);

    }

    @Override
    public void deletePieceByPosition(String position) {
        String query = "DELETE FROM board WHERE piece_position = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, position);
    }

    @Override
    public void addPieceByPosition(int roomId, Position position, Piece piece) {
        String query = "INSERT INTO board(room_id, piece_name, piece_position, piece_color) VALUES (?, ?, ?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, roomId, piece.getSymbol(), position.getPosition(), piece.getColor().name());
    }

    @Override
    public Piece findPieceByPosition(String position) {
        String query = "SELECT piece_name FROM board WHERE piece_position = ?";

        RowMapper<Piece> rm = rs -> {
            if (!rs.next()) {
                return Blank.getInstance();
            }
            return PieceMapper.getInstance().findDBPiece(rs.getString("piece_name"));
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQuery(query, rm, position);
    }

    @Override
    public Map<Position, Piece> findAllPiecesById(int roomId) {
        String query = "SELECT piece_name, piece_position, piece_color FROM board WHERE room_id = ?";

        RowMapper<Map<Position, Piece>> rm = rs -> {
            Map<Position, Piece> pieces = new HashMap<>();

            while (rs.next()) {
                String name = rs.getString("piece_name");
                String position = rs.getString("piece_position");
                pieces.put(Position.of(position), PieceMapper.getInstance().findDBPiece(name));
            }

            return pieces;
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQuery(query, rm, roomId);
    }
}
