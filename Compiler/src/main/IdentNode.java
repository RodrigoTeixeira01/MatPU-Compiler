package main;

public class IdentNode extends Node {

	public Token ident;

	public IdentNode(Token ident) {
		this.ident = ident;
	}

	@Override
	public String toString() {
		return "IdentNode[ident = " + ident + "]";
	}
	
	@Override
	public void compile() {
		String varName = ident.data;
		if(varName.equals("ret")) {
			System.out.println("	ADD r4 r0 r3");
			return;
		}
		int indexInStack = Main.variables.get(varName);
		int actualIndex = indexInStack - Main.stackSize;
		System.out.println("	LOD r15 r3 " + actualIndex);
		// temp 3 = memory[stack pointer + actualIndex]
	}

}
