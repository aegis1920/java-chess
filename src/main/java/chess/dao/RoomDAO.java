package chess.dao;

import java.sql.SQLException;
import java.util.List;

import chess.domain.Color;
import chess.domain.room.Room;

public interface RoomDAO {
    void addRoom(String roomName, String roomColor);

    void removeRoomById(int roomId);

    void updateRoomColorById(int roomId, Color roomColor);

    Room findRoomById(int roomId);

    int findRoomIdByRoomName(String roomName);

    List<Room> findAllRoom();

    Color findRoomColorById(int roomId);
}
