package mainPackage;

public class Game {
	private int boardRow;
	private int boardCol;

	public Game() {
		boardRow = GameSettings.BOARD_ROW;
		boardCol = GameSettings.BOARD_COL;
	}

	public String[][] initialiseBoard() {
		String[][] board = new String[boardRow][boardCol];
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				if (i == 0 || i == boardRow - 1 || j == 0 || j == boardCol - 1)
					board[i][j] = "E";
				else
					board[i][j] = "-";
			}
		}
		
		board[4][4] = "S";
		return board;
	}

}
