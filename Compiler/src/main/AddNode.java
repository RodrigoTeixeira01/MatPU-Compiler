package main;

public class AddNode extends Node {

	private Node a;
	private Node b;

	public AddNode(Node a, Node b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "AddNode[a = " + a + ", b = " + b + "]";
	}
	
	@Override
	public void compile() {
		b.compile();
		System.out.println("	ADD r3 r0 r2"); // mov value to r2
		a.compile();
		System.out.println("	ADD r3 r2 r3"); // add value in r3 with value in r2
	}

}
