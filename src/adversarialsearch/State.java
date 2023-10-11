package adversarialsearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;


public class State {
	public char[][] board; // the board as a 2D character array
	public int[] agentX; // the x−coordinates of the agents
	public int[] agentY; // the y−coordinates of the agents
	public int[] score; // the amount of food eaten by each agent
	public int turn; // who’s turn it is, agent 0 or agent 1
	public int food; // the total amount of food still available
	public int nRows;
	public int nCols;
	
	public State() {
		this.score = new int[] {0, 0};
		this.turn = 0;
	}
	
	public State copy() {
		State stateCopy = new State();
		stateCopy.board = Arrays.stream(this.board).map(char[]::clone).toArray(char[][]::new);
		stateCopy.agentX = Arrays.stream(this.agentX).toArray();
		stateCopy.agentX = Arrays.stream(this.agentY).toArray();
		stateCopy.turn = this.turn;
		stateCopy.food = this.food;
		stateCopy.nRows = this.nRows;
		stateCopy.nCols = this.nCols;
		return stateCopy;
	}
	
	private int[] findOnBoard(char c) {
		for (int i = 0; i < nRows; ++i)
			for (int j = 0; j < nRows; ++j)
				if (board[i][j] == c)
					return new int[] {i, j};
		return null;
	}
	
	public void read(String file) {	
		try {
			RandomAccessFile boardFile = new RandomAccessFile(file, "r");
			String line = boardFile.readLine();
			String[] dimensions = line.split(" ", 2);
			this.nRows = Integer.parseInt(dimensions[0]);
			this.nCols = Integer.parseInt(dimensions[1]);
			this.board = new char[nRows][nCols];
			for (int i = 0; i < nRows; i++) {
				line = boardFile.readLine();
				for (int j = 0; j < nCols; j++) {
					this.board[i][j] = line.charAt(j);
				}
			}
			boardFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.food = 0;
		for (int i = 0; i < nRows; ++i)
			for (int j = 0; j < nRows; ++j)
				if (board[i][j] == '*')
					++this.food;
		this.agentX = this.findOnBoard('A');
		this.agentY = this.findOnBoard('B');
	}
	
	public String toString() {
		String output = "";
		for (int i = 0; i < nRows; i++) {
			String row = new String(this.board[i]);
			output += row + "\n";
		}
		return output;
	}
	
	public State copy() {
		try {
			return 0;
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
}
