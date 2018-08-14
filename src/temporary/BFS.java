package temporary;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	// direction array
	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private String[][] board = new String[][] { { "S", "#", "-" }, 
		{ "-", "#", "-" }, 
		{ "-", "-", "E" }, };

	private int[][] visited;
	private int[][] distance;

	public BFS() {
		bfs();
		printDistance();
	}

	private void printDistance() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(i + " " + j + " " + distance[i][j]); // 0
																		// means
																		// not
																		// visited
			}
		}
	}

	private Position getSource() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals("S"))
					return new Position(i, j);
			}
		}

		return null;
	}

	private int[][] initilizeWithValue(int value) {
		int[][] arr = new int[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				arr[i][j] = value; // 0 means not visited
			}
		}
		return arr;
	}

	private boolean isValidDirection(int tx, int ty, int[][] visited) {
		if (tx >= 3 || tx < 0)
			return false;
		if (ty >= 3 || ty < 0)
			return false;
		if (board[tx][ty].equals("#"))
			return false;
		if (visited[tx][ty] == 1)
			return false;

		return true;
	}

	private void bfs() {
		Position source = getSource();
		Queue<Position> q = new LinkedList();
		q.add(source);
		visited = initilizeWithValue(0);
		distance = initilizeWithValue(-1);

		visited[source.row][source.row] = 1;
		distance[source.row][source.row] = 0;

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
				}
			}
		}

	}

}
