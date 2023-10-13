package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State();
		state.read("data/board.txt");
		System.out.print(state.toString());
		System.out.print("---------\n");
//		System.out.println(state.value(0));
//		System.out.println(state.value(1));
//		System.out.print(state.toString());
//		System.out.print(state.food);
//		System.out.print("\n");
//		System.out.print(state.score[0]);
//		System.out.print(" ");
//		System.out.print(state.score[1]);
//		System.out.print("\n");
//		System.out.print(state.moves);
//		System.out.print("\n");
//		System.out.print(state1.toString());
		Game g = new Game();
		g.test();
	}
}
