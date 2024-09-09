package main;

public class LiteralNode extends Node {

	public LiteralToken literal;

	public LiteralNode(LiteralToken literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return "LiteralNode[literal = " + literal + "]";
	}

	@Override
	protected void compile() {
		System.out.println("	LDI r3 " + literal.data);
		
	}

}
