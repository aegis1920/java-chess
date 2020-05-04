package chess.service;

import java.sql.SQLException;
import java.util.List;

import chess.dao.GameDAO;
import chess.dao.GameDAOImpl;
import chess.dao.RoomDAO;
import chess.dao.RoomDAOImpl;
import chess.domain.Color;
import chess.domain.GameManager;
import chess.domain.PieceScore;
import chess.domain.board.Position;
import chess.domain.piece.Pieces;
import chess.dto.PiecesResponseDTO;

public class GameService {
    private static final GameService GAME_SERVICE = new GameService();

    private GameService() {
    }

    public static GameService getInstance() {
        return GAME_SERVICE;
    }

    public void initialize(int roomId) {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        RoomDAO roomDAO = RoomDAOImpl.getInstance();

        roomDAO.updateRoomColorById(roomId, Color.WHITE);
        Pieces pieces = new Pieces(Pieces.initPieces());
        gameDAO.addAllPiecesById(roomId, pieces);
    }

    public void movePiece(int roomId, String sourcePosition, String targetPosition) {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        RoomDAO roomDAO = RoomDAOImpl.getInstance();

        Pieces pieces = new Pieces(gameDAO.findAllPiecesById(roomId));
        GameManager gameManager = new GameManager(pieces, roomDAO.findRoomColorById(roomId));
        gameManager.moveFromTo(Position.of(sourcePosition), Position.of(targetPosition));
        roomDAO.updateRoomColorById(roomId, gameManager.getCurrentColor());

        gameDAO.removeAllPiecesById(roomId);
        gameDAO.addAllPiecesById(roomId, pieces);
    }

    public double getScore(int roomId, Color color) {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        Pieces pieces = new Pieces(gameDAO.findAllPiecesById(roomId));
        GameManager gameManager = new GameManager(pieces, color);

        return PieceScore.calculateByColor(gameManager, color);
    }

    public PiecesResponseDTO getPiecesResponseDTO(int roomId) {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        Pieces pieces = new Pieces(gameDAO.findAllPiecesById(roomId));
        return new PiecesResponseDTO(pieces);
    }

    public boolean isKingDead(final int roomId) {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        RoomDAO roomDAO = RoomDAOImpl.getInstance();

        Pieces pieces = new Pieces(gameDAO.findAllPiecesById(roomId));
        GameManager gameManager = new GameManager(pieces, roomDAO.findRoomColorById(roomId));
        return gameManager.isKingDead();
    }

    public String getCurrentColor(final int roomId) {
        RoomDAO roomDAO = RoomDAOImpl.getInstance();
        String hi = roomDAO.findRoomColorById(roomId).name();
        System.out.println("hi : " + hi);
        return hi;
    }

    public List<String> getMovablePositions(final int roomId, final String sourcePosition) throws SQLException {
        GameDAO gameDAO = GameDAOImpl.getInstance();
        RoomDAO roomDAO = RoomDAOImpl.getInstance();

        Pieces pieces = new Pieces(gameDAO.findAllPiecesById(roomId));
        GameManager gameManager = new GameManager(pieces, roomDAO.findRoomColorById(roomId));

        return gameManager.getMovablePositions(Position.of(sourcePosition));
    }
}
