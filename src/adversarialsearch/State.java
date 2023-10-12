package adversarialsearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Vector;


public class State {
	public char[][] board; // the board as a 2D character array
	public int[] agentX; // the x−coordinates of the agents
	public int[] agentY; // the y−coordinates of the agents
	public int[] score; // the amount of food eaten by each agent
	public int turn; // who’s turn it is, agent 0 or agent 1
	public int food; // the total amount of food still available
	public int nRows; // number of board rows
	public int nCols; // number of board columns
	
	public State() {
		this.score = new int[] {0, 0};
		this.turn = 0;
	}
	
	public State copy() {
		State stateCopy = new State();
		stateCopy.board = Arrays.stream(this.board).map(char[]::clone).toArray(char[][]::new);
		stateCopy.agentX = Arrays.stream(this.agentX).toArray();
		stateCopy.agentY = Arrays.stream(this.agentY).toArray();
		stateCopy.score = Arrays.stream(this.score).toArray();
		stateCopy.turn = this.turn;
		stateCopy.food = this.food;
		stateCopy.nRows = this.nRows;
		stateCopy.nCols = this.nCols;
		return stateCopy;
	}
	
	public void read(String file) {	
		try {
			this.agentX = new int[] {-1, -1}; // -1 is for not set
			this.agentY = new int[] {-1, -1}; // -1 is for not set
			
			RandomAccessFile boardFile = new RandomAccessFile(file, "r");
			String line = boardFile.readLine();
			String[] dimensions = line.split(" ", 2);
			this.nRows = Integer.parseInt(dimensions[0]);
			this.nCols = Integer.parseInt(dimensions[1]);
			this.board = new char[nRows][nCols];
			for (int i = 0; i < nRows; i++) {
				line = boardFile.readLine();
				for (int j = 0; j < nCols; j++) {
					if (line.charAt(j) == 'A') {
						this.agentX[0] = i;
						this.agentY[0] = j;
						this.board[i][j] = ' ';
					} else if (line.charAt(j) == 'B') {
						this.agentX[1] = i;
						this.agentY[1] = j;
						this.board[i][j] = ' ';
					} else {
						this.board[i][j] = line.charAt(j);
					}
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
	}
	
	public String toString() {
		String output = "";
		for (int i = 0; i < nRows; i++) {
			String row = new String(this.board[i]);
			output += row + "\n";
		}
		return output;
	}
	
	public Vector<String> legalMoves(int agent) {
		Vector<String> ans = new Vector<String>();
		
		if (this.agentX[agent] >= 1 && this.board[this.agentX[agent] - 1][this.agentY[agent]] != '#')
			ans.add("up");
		if (this.agentY[agent] + 1 < this.nCols && this.board[this.agentX[agent]][this.agentY[agent] + 1] != '#')
			ans.add("right");
		if (this.agentX[agent] + 1 < this.nRows && this.board[this.agentX[agent] + 1][this.agentY[agent]] != '#')
			ans.add("down");
		if (this.agentY[agent] >= 1 && this.board[this.agentX[agent]][this.agentY[agent] - 1] != '#')
			ans.add("left");
		
		if (this.board[this.agentX[agent]][this.agentY[agent]] == '*')
			ans.add("eat");
		if (this.board[this.agentX[agent]][this.agentY[agent]] == ' ')
			ans.add("block");
		
		return ans;
	}
	
	public Vector<String> legalMoves() {
		return this.legalMoves(this.turn);
	}
	
	public boolean isLeaf() {
		// return false;
	}
}
