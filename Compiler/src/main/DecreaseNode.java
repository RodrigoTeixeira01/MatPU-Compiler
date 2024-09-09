package main;

public class DecreaseNode extends Node {
	
	public String varName;

	public DecreaseNode(String varName) {
		this.varName = varName;
	}

	@Override
	public String toString() {
		return "DecreaseNode[varName = "+ varName + "]";
	}

	@Override
	protected void compile() {
		int indexInStack = Main.variables.get(varName);
		int actualIndex = indexInStack - Main.stackSize;
		// load the variable into register 3.
		System.out.println("	LOD r15 r3 " + actualIndex);
		// subtract register 3 with register 14 and put in register 3
		System.out.println("	SUB r3 r14 r3");
		// store value back to place
		System.out.println("	STR r15 r3 " + actualIndex);
	}

}
