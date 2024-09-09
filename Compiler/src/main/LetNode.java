package main;

public class LetNode extends Node {
	IdentNode var;
	Node value;

	public LetNode(String varName, Node value) {
		this.var = new IdentNode(new IdentToken(varName));
		this.value = value;
	}

	@Override
	public String toString() {
		return "LetNode[var = " + var + ", value = " + value + "]";
	}

	@Override
	public void compile() {
		if (Main.variables.containsKey(var.ident.data)) {
			throw new DuplicateVariableException("Variable " + var + " already defined.");
		}
		Main.variables.put(var.ident.data, Main.stackSize);
		push(value);
	}

}
