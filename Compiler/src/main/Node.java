package main;

public abstract class Node {

	public static final Node NOP = new NoOperationNode();

	public static Node from(Token first) {
		if(first instanceof LiteralToken literal) {
			return new LiteralNode(literal);
		}
		if(first.getClass() == IdentToken.class) {
			return new IdentNode(first);
		}
		return null;
	}
	
	@Override
	public String toString() {
		throw new RuntimeException("MISSING toString on " + this.getClass().getSimpleName());
	}

	protected abstract void compile();
	
	protected static void push(Node node) {
		node.compile();
		Main.stackSize++;
		System.out.println("	STR r15 r3"); // memory[stack pointer] = register 3 (arithmetic output / literal placing)
		System.out.println("	ADD r15 r14 r15"); // inc stack pointer: stack pointer = stack pointer + register 14 (constant 1)
	}

}
