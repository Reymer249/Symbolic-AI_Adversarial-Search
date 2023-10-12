package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State();
		state.read("data/board.txt");
		System.out.print(state.toString());
		System.out.print("---------\n");
		state.execute("up");
		state.execute("block");
		state.execute("eat");
		System.out.print(state.toString());
		System.out.print(state.food); // TODO: fix food count
		System.out.print("\n");
		System.out.print(state.score);
		System.out.print("\n");
		System.out.print(state.moves);
		System.out.print("\n");
		Game g=new Game();
		g.test();
	}
}
