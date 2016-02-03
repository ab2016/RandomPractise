import java.util.Scanner;

public class TicTacToe {

	static int n = 3;
	static char[][] board = new char[n][n];
	private static char currentPlayer = 'X';

	public static void main(String[] args) {
		initializeBoard(n);

		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("Give x and Y ordinate: eg: 1 2");
			int x = in.nextInt();
//			System.out.println("Give y co ordinate");
			int y = in.nextInt();
			
			if(checkInvalid(x,y)){
				continue;
			}
			
			if( !checkEmpty(x,y)){
				System.out.println("Already entered. Give Another");
				printBoard();
				continue;
			}
			placeMark(x, y);
			printBoard();
			checkForWin();
			changePlayer();

		}
	}

	private static boolean checkInvalid(int x, int y) {
		if(x >=0 && x<3 || y>=0 && y <3){
		return false;
		}
		System.out.println("Invalid co-ordinates. should be 0/1/2");
		printBoard();
		return true;
	}

	private static boolean checkEmpty(int x, int y) {
		return board[x][y] == '-';
		
	}

	public static void checkForWin() {

		if (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()) {
			System.out.println("Player :-" + currentPlayer + " Won the game");
			System.exit(0);
		}
		
		if(isBoardFull()){
			System.out.println("Tie.");
			System.exit(0);
		}

	}

	private static boolean checkRowsForWin() {
		for (int i = 0; i < n; i++) {
			if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkColumnsForWin() {
		for (int i = 0; i < n; i++) {
			if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkDiagonalsForWin() {
		return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true)
				|| (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
	}

	private static boolean checkRowCol(char c1, char c2, char c3) {
		return ((c1 != '-') && (c1 == c2) && (c2 == c3));
	}

	private static boolean isBoardFull() {
		boolean isFull = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == '-') {
					isFull = false;
				}
			}
		}
		return isFull;
	}

	private static void printBoard() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void initializeBoard(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = '-';
			}
		}
	}

	public static void changePlayer() {
		System.out.println("Player changed");
		if (currentPlayer == 'X') {
			currentPlayer = 'O';
		} else {
			currentPlayer = 'X';
		}
	}

	public static boolean placeMark(int row, int col) {

		if ((row >= 0) && (row < n)) {
			if ((col >= 0) && (col < n)) {
				if (board[row][col] == '-') {
					board[row][col] = currentPlayer;
					return true;
				}
			}
		}

		return false;
	}
}
