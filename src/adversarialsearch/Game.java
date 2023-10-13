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
//			System.out.println(s.turn);
//			System.out.println(best_state);
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
//			System.out.println(s.turn);
//			System.out.println(worst_state);
			return worst_state;
		}
	}
	
	public void test() {
//		System.out.println("\nGame.test not fully implemented yet\n");
		
		State out = minimax(b, b.turn, 8, 0);
		System.out.println(out);
		System.out.println(out.moves);
//		System.out.println(out.agentX[0] + " " + out.agentY[0]);
//		System.out.println(out.agentX[1] + " " + out.agentY[1]);
//		System.out.println(out.moves);
//		System.out.println(out.score[0] + " " + out.score[1]);
		
		
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
