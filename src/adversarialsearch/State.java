package adversarialsearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;


public class State implements Serializable {
	private static final long serialVersionUID = 1L; // the variable used for the serialization 
	// (when copy state)
	public char[][] board; // the board as a 2D character array
	public int[] agentX; // the x−coordinates of the agents
	public int[] agentY; // the y−coordinates of the agents
	public int[] score; // the amount of food eaten by each agent
	public int turn; // who’s turn it is, agent 0 or agent 1
	public int food; // the total amount of food still available
	public int nRows; // number of board rows
	public int nCols; // number of board columns
	public Vector<String> moves; // list of executed actions
	
	public State() {
		this.score = new int[] {0, 0};
		this.turn = 0;
		this.moves = new Vector<String>();
	}
	
	public State copy() {
		/** This method deals with the copying of the object. We decided to user serialization
		 * due to it's compactness and scalability. For that the class State was defined as a 
		 * serializable class.*/
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
				
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			State copy = (State)ois.readObject();
			
			return copy;
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void read(String file) {	
		/** Method to read the board from the file with the <file>
		 * location and also write down all needed parameters (e.g. food). */
		try {
			this.agentX = new int[] {-1, -1}; // -1 is for not set
			this.agentY = new int[] {-1, -1}; // -1 is for not set
			this.food = 0;
			
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
						if (line.charAt(j) == '*') {
							this.food++;
						}
						this.board[i][j] = line.charAt(j);
					}
				}
			}
			boardFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String[][] tmpBoard = new String[this.nRows][this.nCols];
		for (int i = 0; i < this.nRows; i++) {
	        for (int j = 0; j < this.nCols; j++) {
	        	tmpBoard[i][j] = String.valueOf(this.board[i][j]);
	        	// to get normal output: comment the 2 if's below
	        	if (i == this.agentX[0] && j == this.agentY[0])
	        		if (tmpBoard[i][j].equals(" "))
	        			tmpBoard[i][j] = "A";
	        		else
	        			tmpBoard[i][j] = "(" + tmpBoard[i][j].replace("(","").replace(")","") + "A)";
	        	if (i == this.agentX[1] && j == this.agentY[1]) {
	        		if (tmpBoard[i][j].equals(" "))
	        			tmpBoard[i][j] = "B";
	        		else
	        			tmpBoard[i][j] = "(" + tmpBoard[i][j].replace("(","").replace(")","") + "B)";
	        	}
	        }
	    }
		
		String output = "";
		for (int k = 0; k < nRows; k++) {
			String row = String.join("", tmpBoard[k]);
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
	
	public void execute(String action) {
		// executing action
		switch (action) {
			case "up":
				this.agentX[this.turn]--;
				break;
			case "right":
				this.agentY[this.turn]++;
				break;
			case "down":
				this.agentX[this.turn]++;
				break;
			case "left":
				this.agentY[this.turn]--;
				break;
			case "eat":
				this.board[this.agentX[this.turn]][this.agentY[this.turn]] = ' ';
				this.score[this.turn]++;
				this.food--;
				break;
			case "block":
				this.board[this.agentX[this.turn]][this.agentY[this.turn]] = '#';
				break;
		}
		this.moves.addElement(action); // adding move to the list of moves done
		this.turn = 1 - this.turn; // passing the turn
	}
	
	public boolean isLeaf() {
		return (this.legalMoves().size() == 0 || this.food == 0);
	}
	
	public double value(int agent) {
		if (this.legalMoves(1-agent).size() == 0)
			return 1;
		if (this.legalMoves(agent).size() == 0)
			return -1;
		if (this.food == 0)
			if (this.score[agent] > this.score[1 - agent])
				return 1;
			else if (this.score[agent] < this.score[1 - agent])
				return -1;
		return 0;
	}
}
