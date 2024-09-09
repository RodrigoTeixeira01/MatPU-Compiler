package main;

public class RunNode extends Node {
	private String funcName;
	
	public RunNode(String funcName) {
		this.funcName = funcName;
	}

	@Override
	public String toString() {
		return "RunNode[funcName = " + funcName + "]";
	}

	@Override
	protected void compile() {
		System.out.println("	CAL .startfunc" + funcName);
		
	}
}
