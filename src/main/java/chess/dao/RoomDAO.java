package chess.dao;

import java.sql.SQLException;
import java.util.List;

import chess.domain.Color;
import chess.domain.room.Room;

public interface RoomDAO {
    void addRoom(String roomName, String roomColor) throws SQLException;

    void removeRoomById(int roomId) throws SQLException;

    void updateRoomColorById(int roomId, Color roomColor) throws SQLException;

    Room findRoomById(int roomId) throws SQLException;

    int findRoomIdByRoomName(String roomName) throws SQLException;

    List<Room> findAllRoom() throws SQLException;

    Color findRoomColorById(int roomId) throws SQLException;
}
