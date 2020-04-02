package chess.domain.board;

import java.util.Arrays;

/**
 *    체스판 행을 의미하는 enum입니다.
 *
 *    @author AnHyungJu, LeeHoBin
 */
public enum Rank {
	EIGHT(8),
	SEVEN(7),
	SIX(6),
	FIVE(5),
	FOUR(4),
	THREE(3),
	TWO(2),
	ONE(1);

	private int rank;

	Rank(int rank) {
		this.rank = rank;
	}

	public static Rank of(int row) {
		return Arrays.stream(Rank.values())
			.filter(rank -> rank.rank == row)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("잘못입력하셨습니다!"));
	}

	public int getRank() {
		return rank;
	}
}
