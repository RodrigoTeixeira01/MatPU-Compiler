package main;

public class WhileNode extends Node {

	private Node cond;
	private Node code;
	private static int whileCount = 0;

	public WhileNode(Node cond, Node code) {
		this.cond = cond;
		this.code = code;
	}

	@Override
	public String toString() {
		return "WhileNode[cond = " + cond + ", code = " + code + "]";
	}

	@Override
	protected void compile() {
		int thisWhileNumber = whileCount ++;
		System.out.println(".whilestart" + thisWhileNumber);
		cond.compile();
		System.out.println("	BRH LT .whileend" + thisWhileNumber);
		code.compile();
		System.out.println("	JMP .whilestart" + thisWhileNumber);
		System.out.println(".whileend" + thisWhileNumber);
		
	}
	

}
