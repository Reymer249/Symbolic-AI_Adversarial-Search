package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State();
		state.read("data/board.txt");
		System.out.print(state.toString());
		System.out.print("---------\n");
		Game g = new Game();
		g.test();
	}
}
