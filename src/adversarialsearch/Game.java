package adversarialsearch;

import java.util.Vector;

public class Game {
	State b;
	
	public Game() {
		b=new State();
		b.read("data/board.txt");
	}
	
	public State minimax(State s, int forAgent, int maxDepth, int depth) {
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		if (s.turn == forAgent) {
			double maxEval = -2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			int n_children = legalMoves.size(); // number of children nodes
			State best_state = new State();
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = minimax(child_state, forAgent, maxDepth, depth+1);
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
				child_state = minimax(child_state, forAgent, maxDepth, depth+1);
				double value = child_state.value(forAgent);
				if (value < minEval) {
					minEval = value;
					worst_state = child_state;
				}
			}
			return worst_state;
		}
	}
	
	public State alphabeta(State s, int forAgent, int maxDepth, int depth, double alpha, double beta) {
		if (depth == maxDepth || s.isLeaf()) {
			return s;
		}
		
		if (s.turn == forAgent ) {
			double maxEval = -2;
			Vector<String> legalMoves =  s.legalMoves(s.turn);
			int n_children = legalMoves.size();
			State best_state = new State();
			State child_state;
			String action;
			for (int i = 0; i < n_children; i++) {
				action = legalMoves.get(i);
				child_state = s.copy();
				child_state.execute(action);
				child_state = alphabeta(child_state, forAgent, maxDepth, depth+1, alpha, beta);
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
				child_state = alphabeta(child_state, forAgent, maxDepth, depth+1, alpha, beta);
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
	
	public void test() {
		int depth = 15;
		
		System.out.println("Alpha-beta");
		State out1 = alphabeta(b, b.turn, depth, 0, -2, 2);
		System.out.println(out1);
		System.out.println("Score: " + out1.value(0));
		System.out.println("Agent A: " + out1.agentX[0] + " " + out1.agentY[0]);
		System.out.println("Agent B: " + out1.agentX[1] + " " + out1.agentY[1]);
		System.out.println("Game log:" + out1.moves);
		System.out.println("Scores: "+ out1.score[0] + " " + out1.score[1]);
		System.out.println("Food: " + out1.food);
		
		System.out.print("---------\n");
		
		System.out.println("Minimax");
		State out2 = minimax(b, b.turn, depth, 0);
		System.out.println(out2);
		System.out.println("Score: " + out2.value(0));
		System.out.println("Agent A: " + out2.agentX[0] + " " + out2.agentY[0]);
		System.out.println("Agent B: " + out2.agentX[1] + " " + out2.agentY[1]);
		System.out.println("Game log:" + out2.moves);
		System.out.println("Scores: "+ out2.score[0] + " " + out2.score[1]);
		System.out.println("Food: " + out2.food);
		
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
	
}
