package main;

public class SetNode extends Node {
	String ident;
	Node value;

	public SetNode(String varName, Node value) {
		this.ident = varName;
		this.value = value;
	}

	@Override
	public String toString() {
		return "SetNode[var = " + ident + ", value = " + value + "]";
	}

	@Override
	protected void compile() {
		int indexInStack = Main.variables.get(ident);
		int actualIndex = indexInStack - Main.stackSize; // offset from runtime stack pointer
		value.compile();
		System.out.println("	STR r15 r3 " + actualIndex);
		
	}
}
