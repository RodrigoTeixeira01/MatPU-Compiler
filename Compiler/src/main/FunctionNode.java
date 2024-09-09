package main;

public class FunctionNode extends Node {
	public String funcName;
	public Node code;

	public FunctionNode(String funcName, Node code) {
		this.funcName = funcName;
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "FunctionNode[funcName = " + funcName + ", code = " + code + "]";
	}

	@Override
	protected void compile() {
		// idc I tried but the only way I can think of is using labels.
		System.out.println("	JMP .endfunc" + funcName);
		System.out.println(".startfunc" + funcName);
		code.compile();
		System.out.println("	RET"); // if ret is not present, it will return from here.
		System.out.println(".endfunc" + funcName);
		
	}

}
