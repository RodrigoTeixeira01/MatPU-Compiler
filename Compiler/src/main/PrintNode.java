package main;

public class PrintNode extends Node {
	private Node value;

	public PrintNode(Node value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "PrintNode[value = "+value+"";
	}
	
	@Override
	public void compile() {
		value.compile();
		System.out.println("	STR r13 r3"); // move value in register 3 (arithmetic results, literal placing, variable value placing) to memory[register 13 (constant 250)].
	}
}
