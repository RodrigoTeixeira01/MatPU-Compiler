package main;

import java.util.HashMap;
import java.util.LinkedList;

public class ScopeNode extends Node {
	public LinkedList<Node> nodes;
	
	public ScopeNode(LinkedList<Node> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public String toString() {
		return "ScopeNode[nodes = "+nodes+"]";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void compile() {
		 HashMap<String, Integer> oldVariables = Main.variables;
		 int oldStackSize = Main.stackSize;
		 Main.variables = (HashMap<String, Integer>) Main.variables.clone();
		 for(Node node: nodes) {
			 node.compile();
		 }
		 Main.variables = oldVariables;
		 System.out.println("	ADI r15 " + ((byte) oldStackSize-Main.stackSize));
		 Main.stackSize = oldStackSize;
	}
}
