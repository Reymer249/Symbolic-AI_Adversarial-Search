package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		State state = new State(new int[]{2, 2}, new int[]{3, 2}, new int[]{}, 0, 2);
		state.read("/Users/admin/Desktop/Jaba/ass1/data/board.txt");
		System.out.print(state.toString());
		//Game g=new Game();
		//g.test();
	}
}
