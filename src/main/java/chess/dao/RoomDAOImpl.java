package chess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import chess.domain.Color;
import chess.domain.room.Room;

public class RoomDAOImpl implements RoomDAO {
    private static final RoomDAO ROOM_DAO = new RoomDAOImpl();

    public static RoomDAO getInstance() {
        return ROOM_DAO;
    }

    @Override
    public void addRoom(String roomName, String roomColor) {
        String query = "INSERT INTO room(room_name, room_color) VALUES (?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, roomName, roomColor);
    }

    @Override
    public void removeRoomById(int roomId) {
        String query = "DELETE FROM room WHERE room_id = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, roomId);
    }

    @Override
    public void updateRoomColorById(int roomId, Color roomColor) {
        String query = "UPDATE room SET room_color = ? WHERE room_id = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.executeUpdate(query, roomId, roomColor);
    }

    @Override
    public Room findRoomById(int roomId) {
        String query = "SELECT room_id, room_name, room_color FROM room WHERE room_id = ?";

        RowMapper<Room> rm = rs -> {
            if (!rs.next()) {
                return null;
            }
            return new Room(
                rs.getInt("room_id"),
                rs.getString("room_name"),
                rs.getString("room_color")
            );
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQuery(query, rm, roomId);
    }

    @Override
    public int findRoomIdByRoomName(String roomName) {
        String query = "SELECT room_id FROM room WHERE room_name = ?";

        RowMapper<Integer> rm = rs -> {
            if (!rs.next()) {
                return 0;
            }

            return rs.getInt("room_id");
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQuery(query, rm, roomName);
    }

    @Override
    public List<Room> findAllRoom() {
        String query = "SELECT room_id, room_name, room_color FROM room";

        RowMapper<Room> rm = rs -> {
            int roomId = rs.getInt("room_id");
            String roomName = rs.getString("room_name");
            String roomColor = rs.getString("room_color");

            return new Room(roomId, roomName, roomColor);
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQueryOfList(query, rm);
    }

    @Override
    public Color findRoomColorById(int roomId) {
        String query = "SELECT room_color FROM room WHERE room_id = ?";

        RowMapper<Color> rm = rs -> {
            if (!rs.next()) {
                return null;
            }
            return Color.valueOf(rs.getString("room_color"));
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.executeQuery(query, rm, roomId);
    }
}
