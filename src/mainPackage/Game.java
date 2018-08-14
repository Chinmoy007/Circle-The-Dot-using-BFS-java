package mainPackage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import temporary.Position;

public class Game {
	private int boardRow;
	private int boardCol;

	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private String[][] board;
	private int[][] visited;
	private int[][] distance;
	
	Position source;

	private HashMap<String, Position> parents;

	public Game() {
		boardRow = GameSettings.BOARD_ROW;
		boardCol = GameSettings.BOARD_COL;

		parents = new HashMap<>();
		// Position p = getBestPosition(board);
		// System.out.println(p.row + " " + p.col);
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

		// board[4][4] = "S";
		return board;
	}

	private void printDistance() {
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				if (i == 0 || i == boardRow - 1 || j == 0 || j == boardCol - 1) {

					System.out.println(i + " " + j + " " + distance[i][j]);
				}
			}
		}
	}

	private Position getSource() {
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				if (board[i][j].equals("S"))
					return new Position(i, j);
			}
		}

		return null;
	}

	private int[][] initilizeWithValue(int value) {
		int[][] arr = new int[boardRow][boardCol];

		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				arr[i][j] = value; // 0 means not visited
			}
		}
		return arr;
	}

	private boolean isValidDirection(int tx, int ty, int[][] visited) {
		if (tx >= boardRow || tx < 0)
			return false;
		if (ty >= boardCol || ty < 0)
			return false;
		if (board[tx][ty].equals("#"))
			return false;
		if (visited[tx][ty] == 1)
			return false;

		return true;
	}

	private void bfs() {
		 source = getSource();
		Queue<Position> q = new LinkedList();

		q.add(source);

		visited = initilizeWithValue(0);
		distance = initilizeWithValue(-1);

		visited[source.row][source.col] = 1;
		distance[source.row][source.col] = 0;

		String nodeKey = source.row + "" + source.col;

		parents.put(nodeKey, null);
		while (!q.isEmpty()) {
			Position node = q.peek();
			q.poll();
			for (int i = 0; i < 8; i++) {
				int tx = node.row + fx[i];
				int ty = node.col + fy[i];
				if (isValidDirection(tx, ty, visited)) {
					q.add(new Position(tx, ty));
					distance[tx][ty] = distance[node.row][node.col] + 1;
					visited[tx][ty] = 1;
					nodeKey = tx + "" + ty;
					parents.put(nodeKey, new Position(node.row, node.col));
				}
			}
		}
	}

	private Position getLessCostGoal() {
		int minDistance = Integer.MAX_VALUE;
		int row = -1;
		int col = -1;
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				if (i == 0 || i == boardRow - 1 || j == 0 || j == boardCol - 1) {
					if (minDistance >= distance[i][j]) {
						minDistance = distance[i][j];
						row = i;
						col = j;
					}
				}

			}
		}
		return new Position(row, col);
	}

	private Position getImmediateMove() {
		Position pos = getLessCostGoal();
		
		System.out.println("less: " + pos.row + " " + pos.col);
		Position immediateChild;
		while (true) {
			immediateChild = pos;
			System.out.println("imm: " + immediateChild.row + " " + immediateChild.col);

			String key = pos.row + "" + pos.col;
			pos = parents.get(key);
			if (pos.row == source.row && pos.col == source.col)
				break;
		}

		return immediateChild;
	}

	public int winStatus(int x, int y){
		
	}
	
	public Position getBestPosition(String[][] board) {
		this.board = board;
		bfs();
//		printDistance();
		
		return getImmediateMove();
	}

}
