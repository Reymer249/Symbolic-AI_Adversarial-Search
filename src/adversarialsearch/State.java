package adversarialsearch;

import java.io.IOException;
import java.io.RandomAccessFile;


public class State {
	public char[][] board ; // the board as a 2D character array
	public int[] agentX ; // the x−coordinates of the agents
	public int[] agentY ; // the y−coordinates of the agents
	public int[] score ; // the amount of food eaten by each agent
	public int turn ; // who’s turn it is, agent 0 or agent 1
	public int food ; // the total amount of food still available
	public int nRows;
	public int nCols;
	
	public State(int[] agentX, int[] agentY, int[] score, int turn, int food) {
		this.agentX = agentX;
		this.agentY = agentY;
		this.score = score;
		this.turn = turn;
		this.food = food;
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
