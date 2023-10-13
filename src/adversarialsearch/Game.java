package adversarialsearch;

import java.util.Vector;
import java.util.Collections;

public class Game {
	State b;
	
	public Game() {
		b=new State();
		b.read("data/board.txt");
	}
	
	public State minimaxLastState(State s, int forAgent, int maxDepth, int depth) {
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		if (s.turn == forAgent) {
			double maxEval = -2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			Collections.shuffle(legalMoves);
			int n_children = legalMoves.size(); // number of children nodes
			State best_state = new State();
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = minimaxLastState(child_state, forAgent, maxDepth, depth+1);
				double value = child_state.value(forAgent);
				if (value > maxEval) {
					maxEval = value;
					best_state = child_state;
				}
			}
			return best_state;
		}
		else {
			double minEval = 2;
			Vector<String> legalMoves =  s.legalMoves(s.turn); 
			int n_children = legalMoves.size(); // number of children nodes
			State worst_state = new State(); // We name the variable "worst case" as we look 
			//from the perspective of <forAgent> agent
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = minimaxLastState(child_state, forAgent, maxDepth, depth+1);
				double value = child_state.value(forAgent);
				if (value < minEval) {
					minEval = value;
					worst_state = child_state;
				}
			}
			return worst_state;
		}
	}
	
	public State minimax(State s, int forAgent, int maxDepth, int depth) {
		State sCopy = s.copy();
		sCopy.moves = new Vector<String>();
		State nextState = s.copy();
		State lastState = minimaxLastState(sCopy, forAgent, maxDepth, depth);
		nextState.execute(lastState.moves.get(0));
		return nextState;
	}
	
	public State alphabetaLastState(State s, int forAgent, int maxDepth, int depth, double alpha, double beta) {
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		if (s.turn == forAgent ) {
			double maxEval = -2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			Collections.shuffle(legalMoves);
			int n_children = legalMoves.size();
			State best_state = new State();
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = alphabetaLastState(child_state, forAgent, maxDepth, depth+1, alpha, beta);
				double value = child_state.value(forAgent);
				if (value > maxEval) {
					maxEval = value;
					best_state = child_state;
				}
				if (alpha < value)
					alpha = value;
				if (beta <= alpha)
					break;
			}
			return best_state;
		}
		else {
			double minEval = 2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			int n_children = legalMoves.size();
			State worst_state = new State(); // We name the variable "worst case" as we look 
			//from the perspective of <forAgent> agent
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = alphabetaLastState(child_state, forAgent, maxDepth, depth+1, alpha, beta);
				double value = child_state.value(forAgent);
				if (value < minEval) {
					minEval = value;
					worst_state = child_state;
				}
				if (beta > value)
					beta = value;
				if (beta <= alpha)
					break;
			}
			return worst_state;
		}
	}
	
	public State alphabeta(State s, int forAgent, int maxDepth, int depth, double alpha, double beta) {
		State sCopy = s.copy();
		sCopy.moves = new Vector<String>();
		State nextState = s.copy();
		State lastState = alphabetaLastState(sCopy, forAgent, maxDepth, depth, alpha, beta);
		nextState.execute(lastState.moves.get(0));
		return nextState;
	}
	
	private void printBoard(State board, boolean mode) {
		System.out.println(board);
		System.out.println("Score: " + board.value(0));
		if (mode == true) {
			System.out.println("Agent A: " + board.agentX[0] + " " + board.agentY[0]);
			System.out.println("Agent B: " + board.agentX[1] + " " + board.agentY[1]);
			System.out.println("Game log:" + board.moves);
			System.out.println("Scores: "+ board.score[0] + " " + board.score[1]);
			System.out.println("Food: " + board.food);
		}
		System.out.println();
	}
	
	private void printMoves(State startBoard, State finalBoard) {
		State tmpBoard = startBoard.copy();
		for (int i = 0; i < finalBoard.moves.size(); ++i) {
			tmpBoard.execute(finalBoard.moves.get(i));
			printBoard(tmpBoard, false);
		}
	}
	
	public void test(int depth) {
		System.out.println("Alpha-beta (next move):\n");
		State out1 = alphabeta(b, b.turn, depth, 0, -2, 2);
		printMoves(b, out1);
		
		System.out.print("==========\n");
		
//		System.out.println("Minimax");
//		State out2 = minimax(b, b.turn, depth, 0);
//		printMoves(b, out2);
		
		/* Random moves game */
//		while (!b.isLeaf()){
//			System.out.println(b.toString());
//			System.out.println("Legal moves for agent " + b.turn + ": "+ b.legalMoves() + "\n");
//			System.out.println("Number of food: " + b.food);
//			b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
//		}
//		System.out.println(b.toString());
//		System.out.println("Legal moves for agent " + b.turn + ": "+ b.legalMoves() + "\n");
//		System.out.println("Number of food: " + b.food);
//		b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
		
	}
	
	public void play(int depth) {
		while (!b.isLeaf()) {
			State nextState = alphabeta(b, b.turn, depth, 0, -2, 2);
			
			System.out.println("Agent " + b.turn + " made move " + 
							nextState.moves.get(nextState.moves.size() -1));
			System.out.println(nextState);
			System.out.println("Score: " + nextState.score[0] + " " + nextState.score[1]);
			System.out.print("==========\n");
			b = nextState;
		}
	}
}
