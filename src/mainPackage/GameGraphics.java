package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameGraphics implements Runnable, MouseMotionListener {

	private Display display;
	private int width;
	private int height;

	private String[][] board;

	private Thread thread;
	private BufferStrategy buffer;
	private Graphics g;

	private Game game;

	private int paddingX = 50;
	private int paddingY = 50;

	private int boxSize = 50;

	private int boardRow;
	private int boardCol;

	private int mousePointAtX = -1;
	private int mousePointAtY = -1;

	private String player;
	private String playerCpu = "X";
	private String playerHuman = "O";

	private boolean isGameEnd = false;
	private boolean isGameStarted = false;

	public GameGraphics() {
		this.width = GameSettings.width;
		this.height = GameSettings.height;

		boardRow = GameSettings.BOARD_ROW;
		boardCol = GameSettings.BOARD_COL;

		display = new Display();
		game = new Game();
	}

	private void init() {

		player = playerHuman;

		// black = ImageLoader.loadImage("/Images/bl1.png", BOX_width,
		// BOX_height);
		// white = ImageLoader.loadImage("/Images/w1.png", BOX_width,
		// BOX_height);
		// mouseIndicator = ImageLoader.loadImage("/Images/w1.png", BOX_width,
		// BOX_height);
		// background = ImageLoader.loadImage("/Images/background3.jpg", width,
		// height);
		// logo = ImageLoader.loadImage("/Images/logo.png");

		// display.canvas.addKeyListener(this);
		display.canvas.addMouseMotionListener(this);
		// display.canvas.addMouseListener(this);
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, width, height);

	}

	private void drawBoard(Graphics g) {

		drawBackground(g);
		Color color = Color.WHITE;
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {

				if (board[i][j].equals("-")) {
					color = Color.WHITE;
				} else if (board[i][j].equals("S")) {
					color = Color.CYAN;
				} else if (board[i][j].equals("#")) {
					color = Color.gray;
				}

				if (!board[i][j].equals("E")) {

					int x = (j + 1) * boxSize;
					int y = (i + 1) * boxSize;
					g.setColor(color);
					g.fillOval(x, y, boxSize, boxSize);
				}
			}
		}

	}

	private void mousePointIndicatior(Graphics g) {

		int x = mousePointAtY * boxSize;
		int y = mousePointAtX * boxSize;

		if (!(mousePointAtX == -1 || mousePointAtY == -1) && board[mousePointAtX - 1][mousePointAtY - 1].equals("-")) {

			g.setColor(Color.green);
			g.fillOval(x, y, boxSize, boxSize);
		}
		// g.drawImage(mouseIndicator, x, y, null);

	}

	private void play(Graphics g) {

	}

	private void render() {
		buffer = display.canvas.getBufferStrategy();
		if (buffer == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}

		g = buffer.getDrawGraphics();

		g.clearRect(0, 0, width, height);
		drawBoard(g);
		mousePointIndicatior(g);
		buffer.show();
		g.dispose();

	}

	@Override
	public void run() {
		init();
		board = game.initialiseBoard();
		while (true) {
			render();
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (!isGameEnd && player.equals(playerHuman)) {

			mousePointAtX = y / boxSize;
			mousePointAtY = x / boxSize;

			if (mousePointAtX < 1 || mousePointAtX > boardRow || mousePointAtY < 1 || mousePointAtY > boardCol) {
				mousePointAtX = -1;
				mousePointAtY = -1;
			}
		} else {
			mousePointAtX = -1;
			mousePointAtY = -1;
		}

	}

}
