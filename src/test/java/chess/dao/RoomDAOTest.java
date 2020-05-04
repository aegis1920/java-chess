package chess.dao;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import chess.domain.Color;
import chess.domain.room.Room;

public class RoomDAOTest {
	@Mock
	private RoomDAO roomDAO;

	@BeforeEach
	void setUp() throws SQLException {
		int roomId = roomDAO.findRoomIdByRoomName("hello world");
		roomDAO.removeRoomById(roomId);
		AutoIncrement.applyAutoIncrementToZero();
	}

	@DisplayName("auto increment를 1로 초기화하는 테스트")
	@Test
	void applyAutoIncrementToZeroTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");

		assertThat(roomDAO.findRoomById(roomId)).isNotNull();
	}

	@DisplayName("방의 이름를 가지고 방의 이름을 찾는 메서드 테스트")
	@Test
	void findRoomIdByRoomNameTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");
		assertThat(roomDAO.findRoomById(roomId)).isNotNull();
	}

	@DisplayName("방의 번호를 가지고 방을 찾는 테스트")
	@Test
	void findRoomByIdTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");

		Room room = roomDAO.findRoomById(roomId);

		assertThat(room.getRoomId()).isEqualTo(roomId);
	}

	@DisplayName("방에 입장했을 때 DB에 room을 insert하는 작업")
	@Test
	void addRoomTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");

		Room room = roomDAO.findRoomById(roomId);

		assertThat(room.getRoomName()).isEqualTo("hello world");
	}

	@DisplayName("방을 삭제하고 나서 해당 방을 찾았을 때 Null이 들어가는지 테스트")
	@Test
	public void removeRoomByIdTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");

		roomDAO.removeRoomById(roomId);
		Room room = roomDAO.findRoomById(roomId);

		assertThat(room).isNull();
	}

	@DisplayName("move를 하고나서 상대방 턴이 됐을 때 turn의 값이 바뀌었는지 테스트")
	@Test
	void updateRoomColorByIdTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");
		Color color = Color.BLACK;
		roomDAO.updateRoomColorById(roomId, color);

		Room room = roomDAO.findRoomById(roomId);
		assertThat(room.getTurn()).isEqualTo("BLACK");
	}

	@DisplayName("현재 내 턴을 가져오는 테스트")
	@Test
	void findRoomColorByIdTest() throws SQLException {
		roomDAO.addRoom("hello world", "WHITE");
		int roomId = roomDAO.findRoomIdByRoomName("hello world");
		Color color = roomDAO.findRoomColorById(roomId);
		assertThat(color).isEqualTo(Color.WHITE);
	}
}
