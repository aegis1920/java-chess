package chess.dao;

import java.sql.SQLException;
import java.util.Map;

import chess.domain.board.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;

public interface GameDAO {
    void removeAllPiecesById(int roomId) throws SQLException;

    void addAllPiecesById(int roomId, Pieces pieces) throws SQLException;

    void updatePieceByPosition(String currentPosition, String nextPosition) throws SQLException;

    void deletePieceByPosition(String position) throws SQLException;

    void addPieceByPosition(int roomId, Position position, Piece piece) throws SQLException;

    Piece findPieceByPosition(String position) throws SQLException;

    // TODO : Map을 반환하는 template을 만들면 좋을 것 같은데...
    Map<Position, Piece> findAllPiecesById(int roomId) throws SQLException;
}
