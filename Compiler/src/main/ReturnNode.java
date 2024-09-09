package main;

public class ReturnNode extends Node {
	private Node value;

	public ReturnNode(Node value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ReturnNode[value = "+value+"";
	}

	@Override
	protected void compile() {
		value.compile();
		System.out.println("	add r3 r0 r4");
		System.out.println("	ret");
	}
}
