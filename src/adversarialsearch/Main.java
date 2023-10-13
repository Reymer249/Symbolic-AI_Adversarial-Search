package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State();
		state.read("data/board.txt");
		System.out.println("Initial board:\n");
		System.out.println(state);
		System.out.println("==========\n");
		Game g = new Game();
		g.play(7);
	}
}
