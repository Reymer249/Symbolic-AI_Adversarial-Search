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
		/** This this an implementation of the minimax algorithm, which returns
		 * the best possible final state for agent <forAgent> with the given depth
		 * <maxDepth>. */
		
		// base case: we reached the max depth or the node is a leaf
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		// recursive case
		if (s.turn == forAgent) {  // if it is the turn of the given agent, we maximize
			double maxEval = -2; // we pick -2 as value can't be lower than -1 
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			Collections.shuffle(legalMoves); // shuffle the order of the children
			// The reason for that action is discussed in the "DIscussion" section in the report
			int n_children = legalMoves.size(); // number of children nodes
			State best_state = new State(); // the state with the max value
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {  // consider every children of the node 
				action = legalMoves.get(i);
				child_state = s.copy(); // we work with the copy of the state. The reason for
				// that is discussed in the report ("Task 9" section)
				child_state.execute(action); // executing action to transfer to the child state
				child_state = minimaxLastState(child_state, forAgent, maxDepth, depth+1);
				double value = child_state.value(forAgent);
				if (value > maxEval) {
					maxEval = value;
					best_state = child_state;
				}
			}
			return best_state;
		}
		else {  // if it's a turn of our opponent, we minimize
			double minEval = 2; // we pick 2 as value can't be higher than 1 
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			Collections.shuffle(legalMoves); // shuffle the order of the children
			// The reason for that action is discussed in the "DIscussion" section in the report
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
		/** This method is a "wrap" for the minimaxLastState method. It outputs
		 * the next state of the game if the agent <forAgent> will apply minimax alg.
		 * with depth <maxDepth> and make the most optimal move according to the output
		 * of the algorithm.
		 * The technical details of this method are discussed in the report in the "Task 9"
		 * section.*/
		State sCopy = s.copy(); 
		sCopy.moves = new Vector<String>();  // we copy the provided and clear the log 
		// of the game. The reasons for that are discussed in the "Task 9" section in the report
		State nextState = s.copy();
		State lastState = minimaxLastState(sCopy, forAgent, maxDepth, depth);
		nextState.execute(lastState.moves.get(0));
		return nextState;
	}
	
	public State alphabetaLastState(State s, int forAgent, int maxDepth, int depth, double alpha, double beta) {
		/** This this an implementation of the minimax algorithm with alpha-beta pruning, 
		 * which returns the best possible final state for agent <forAgent> with the given 
		 * depth <maxDepth>. */
		
		// base case: we reached the max depth or the node is a leaf
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		// recursive case
		if (s.turn == forAgent ) { // if it is the turn of the given agent, we maximize
			double maxEval = -2;  // we pick 2 as value can't be higher than 1 
			Vector<String> legalMoves =  s.legalMoves(s.turn);  
			Collections.shuffle(legalMoves); // shuffle the order of the children
			// The reason for that action is discussed in the "DIscussion" section in the report
			int n_children = legalMoves.size(); // number of children nodes
			State best_state = new State();
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) { // consider every children of the node 
				action = legalMoves.get(i);
				child_state = s.copy(); // we work with the copy of the state. The reason for
				// that is discussed in the report ("Task 9" section)
				child_state.execute(action); // execution the action to transfer to the child state
				child_state = alphabetaLastState(child_state, forAgent, maxDepth, depth+1, alpha, beta);
				double value = child_state.value(forAgent);
				// alpha-beta pruning
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
		else {  // if it's a turn of our opponent, we minimize
			double minEval = 2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			Collections.shuffle(legalMoves); // shuffle the order of the children
			// The reason for that action is discussed in the "DIscussion" section in the report
			// shuffle the order of the children
			int n_children = legalMoves.size(); // number of children nodes
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
				// alpha-beta pruning
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
		/** This method is a "wrap" for the alphabetaLastState method. It outputs
		 * the next state of the game if the agent <forAgent> will apply minimax alg. with 
		 * alpha-beta pruning with depth <maxDepth> and make the most optimal move according 
		 * to the output of the algorithm.
		 * The technical details of this method are discussed in the report in the "Task 9"
		 * section.*/
		State sCopy = s.copy();
		sCopy.moves = new Vector<String>(); // we copy the provided and clear the log 
		// of the game. The reasons for that are discussed in the "Task 9" section in the report
		State nextState = s.copy();
		State lastState = alphabetaLastState(sCopy, forAgent, maxDepth, depth, alpha, beta);
		nextState.execute(lastState.moves.get(0));
		return nextState;
	}
	
	private void printBoard(State board, boolean mode) {
		/** This additional method is used for printing the parameters of the game on 
		 * each step. If the <mode> if true, it will output more detailed information 
		 * about the current state.*/
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
		/** This additional method is used for printing each move up to the
		 * best last state in the search tree. It was implemented and used merely 
		 * for testing purposes.*/
		State tmpBoard = startBoard.copy();
		for (int i = 0; i < finalBoard.moves.size(); ++i) {
			tmpBoard.execute(finalBoard.moves.get(i));
			printBoard(tmpBoard, false);
		}
	}
	
	public void test(int depth) {
		/* Alpha-beta test */
		System.out.println("Alpha-beta (next move):\n");
		State out1 = alphabeta(b, b.turn, depth, 0, -2, 2);
		printMoves(b, out1);
		
		System.out.print("==========\n");
		
		/* Minimax test */
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
		/** This is an additional method, which will play a game from the given
		 * state <b>. The agents make move one-by-one and each are using minimax with 
		 * alpha-beta pruning to pick a move. The game goes until there is an end, described
		 * in the "Blocker" game rules.*/
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
