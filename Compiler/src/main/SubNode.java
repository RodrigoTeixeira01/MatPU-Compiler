package main;

public class SubNode extends Node {

	private Node a;
	private Node b;

	public SubNode(Node a, Node b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "SubNode[a = " + a + ", b = " + b + "]";
	}

	@Override
	protected void compile() {
		b.compile();
		System.out.println("	ADD r3 r0 r2");
		a.compile();
		System.out.println("	SUB r3 r2 r3");
	}

}
