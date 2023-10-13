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
			int n_children = legalMoves.size();
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
			if (s.food == 0 && s.score[0] == 2 && s.score[1] == 0)
				System.out.println("0win" + s.value(forAgent));
			else if (s.food == 0 && s.score[1] == 2 && s.score[0] == 0)
				System.out.println("1win" + s.value(forAgent));
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
//		System.out.println("\nGame.test not fully implemented yet\n");
		
		State out1 = alphabeta(b, b.turn, 15, 0, -2, 2);
		System.out.println(out1);
		System.out.println(out1.value(0));
		System.out.println(out1.agentX[0] + " " + out1.agentY[0]);
		System.out.println(out1.agentX[1] + " " + out1.agentY[1]);
		System.out.println(out1.moves);
		System.out.println(out1.score[0] + " " + out1.score[1]);
		System.out.println(out1.food);
		
//		System.out.print("---------\n");
//		
//		State out2 = minimax(b, b.turn, 10, 0);
//		System.out.println(out2);
//		System.out.println(out2.agentX[0] + " " + out2.agentY[0]);
//		System.out.println(out2.agentX[1] + " " + out2.agentY[1]);
//		System.out.println(out2.moves);
//		System.out.println(out2.score[0] + " " + out2.score[1]);
		
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
