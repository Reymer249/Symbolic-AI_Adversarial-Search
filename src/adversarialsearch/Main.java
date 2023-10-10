package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State();
		state.read("data/board.txt");
		System.out.print(state.toString());
		Game g=new Game();
		g.test();
	}
}
