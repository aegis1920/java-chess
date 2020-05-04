package chess.dao;

import java.sql.SQLException;
import java.util.Map;

import chess.domain.board.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;

public interface GameDAO {
    void removeAllPiecesById(int roomId);

    void addAllPiecesById(int roomId, Pieces pieces);

    void updatePieceByPosition(String currentPosition, String nextPosition);

    void deletePieceByPosition(String position);

    void addPieceByPosition(int roomId, Position position, Piece piece);

    Piece findPieceByPosition(String position);

    // TODO : Map을 반환하는 template을 만들면 좋을 것 같은데...
    Map<Position, Piece> findAllPiecesById(int roomId);
}
