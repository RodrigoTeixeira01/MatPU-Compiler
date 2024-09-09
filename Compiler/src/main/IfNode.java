package main;

public class IfNode extends Node {

	private Node cond;
	private Node code;
	private static int ifCount = 0;

	public IfNode(Node cond, Node code) {
		this.cond = cond;
		this.code = code;
	}

	@Override
	protected void compile() {
		cond.compile();
		int thisIfNumber = ifCount++;
		System.out.println("	BRH LT .ifend" + thisIfNumber);
		code.compile();
		System.out.println(".ifend" + thisIfNumber);
	}

}
