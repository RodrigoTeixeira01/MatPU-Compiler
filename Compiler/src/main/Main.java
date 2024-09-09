package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {

	private HashMap<String, Node> macros = new HashMap<>();
	public static HashMap<String, Integer> variables = new HashMap<>();
	public static int stackSize = 0;

	public Main() {
		String code = getCode();

		LinkedList<Token> tokens = tokenise(code);
		System.out.println(tokens);
		LinkedList<Node> parseTree = parseCode(tokens);
		System.out.println(parseTree);
		System.out.println("	LDI r13 250"); // put output memory place constant in register 13
		System.out.println("	LDI r14 1"); // put constant 1 in register 14
		System.out.println("	LDI r15 0"); // restart stack pointer
		for(Node statement: parseTree) {
			statement.compile();
		}
		System.out.println("	HLT"); // halt if not explicit in end of program
	}

	private LinkedList<Node> parseCode(LinkedList<Token> tokens) {
		LinkedList<Node> parseTree = new LinkedList<>();
		while (true) {
			Node statement = parseStatement(tokens, false);
			if (statement == null) {
				break;
			}
			parseTree.add(statement);
		}
		return parseTree;
	}

	private ScopeNode parseScope(LinkedList<Token> tokens) {
		LinkedList<Node> parseTree = new LinkedList<>();
		while (true) {
			Node statement = parseStatement(tokens, true);
			if (statement == null) {
				break;
			}
			parseTree.add(statement);
		}
		return new ScopeNode(parseTree);
	}

	private Node parseStatement(LinkedList<Token> tokens, boolean insideScope) {
		if (tokens == null || tokens.isEmpty())
			return null;
		switch (tokens.getFirst().getClass().getSimpleName()) {
		case "LetToken":
			tokens.removeFirst();
			if (tokens.getFirst().getClass() != IdentToken.class) {
				throw new SyntaxException("Expected Identafier after let.");
			}
			String varName = tokens.removeFirst().data;
			if (tokens.getFirst() instanceof EqualsToken) {
				tokens.removeFirst();
			}
			return new LetNode(varName, parseValue(tokens, false, Token.SEMI));
		case "SemiToken":
			tokens.removeFirst();
			return Node.NOP;
		case "OpenBracketToken":
			tokens.removeFirst();
			return parseScope(tokens);
		case "CloseBracketToken":
			tokens.removeFirst();
			if (insideScope) {
				return null;
			}
			throw new SyntaxException("Unopenned block.");
		case "IdentToken":
			varName = tokens.removeFirst().data;
			if (tokens.getFirst() instanceof EqualsToken) {
				tokens.removeFirst();
			}
			return new SetNode(varName, parseValue(tokens, false, Token.SEMI));
		case "IfToken":
			tokens.removeFirst();
			if (tokens.removeFirst() != Token.OPEN_PARENT) {
				throw new SyntaxException("Expected open parentises after if.");
			}
			Node cond = parseValue(tokens, false, Token.CLOSE_PARENT);
			tokens.removeFirst(); // remove parentises.
			Node code = parseStatement(tokens, insideScope);
			return new IfNode(cond, code);
		case "WhileToken":
			tokens.removeFirst();
			if (tokens.removeFirst() != Token.OPEN_PARENT) {
				throw new SyntaxException("Expected open parentises after while.");
			}
			cond = parseValue(tokens, false, Token.CLOSE_PARENT);
			tokens.removeFirst(); // remove parentises.
			code = parseStatement(tokens, insideScope);
			return new WhileNode(cond, code);
		case "PlusToken":
			tokens.removeFirst();
			if (!(tokens.getFirst() instanceof IdentToken)) {
				throw new SyntaxException("Excepted identafier after +.");
			}
			varName = tokens.removeFirst().data;
			return new IncreaseNode(varName);
		case "MinusToken":
			tokens.removeFirst();
			if (!(tokens.getFirst() instanceof IdentToken)) {
				throw new SyntaxException("Excepted identafier after -.");
			}
			varName = tokens.removeFirst().data;
			return new DecreaseNode(varName);
		case "FunctionToken":
			tokens.removeFirst();
			if (tokens.removeFirst() instanceof IdentToken ident) {
				String funcName = ident.data;
				code = parseStatement(tokens, insideScope);
				return new FunctionNode(funcName, code);
			}
			throw new SyntaxException("Exception Identafier after function.");
		case "MacroToken":
			tokens.removeFirst();
			if (tokens.removeFirst() instanceof IdentToken ident) {
				String macroName = ident.data;
				code = parseStatement(tokens, insideScope);
				macros.put(macroName, code);
				return Node.NOP;
			}
			throw new SyntaxException("Exception Identafier after macro.");
		case "RunToken":
			tokens.removeFirst();
			if (tokens.getFirst() instanceof IdentToken ident) {
				if (macros.containsKey(ident.data)) {
					tokens.removeFirst();
					return macros.get(ident.data);
				}
				return new RunNode(tokens.removeFirst().data);
			}
			throw new SyntaxException("Exception Identafier after run.");
		case "PrintToken":
			tokens.removeFirst();
			return new PrintNode(parseValue(tokens, false, Token.SEMI));
		case "ReturnToken":
			tokens.removeFirst();
			return new ReturnNode(parseValue(tokens, false, Token.SEMI));

		}
		return null;
	}

	private Node parseValue(LinkedList<Token> tokens, boolean flipped, Token terminator) {

		Node first = Node.from(tokens.removeFirst());
		if (tokens.getFirst() == terminator) {
			return first;
		}
		if ((tokens.getFirst() == Token.PLUS && !flipped) || (tokens.getFirst() == Token.MINUS && flipped)) {
			tokens.removeFirst();
			return new AddNode(first, parseValue(tokens, flipped, terminator));
		}
		if ((tokens.getFirst() == Token.MINUS && !flipped) || (tokens.getFirst() == Token.PLUS && flipped)) {
			tokens.removeFirst();
			//System.out.println("DEBUG " + first + " " + tokens);
			return new SubNode(first, parseValue(tokens, !flipped, terminator));
		}
		return null;
	}

	private LinkedList<Token> tokenise(String code) {

		LinkedList<Token> tokens = new LinkedList<>();

		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			switch (c) {
			case '+':
				tokens.add(Token.PLUS);
				break;
			case '-':
				tokens.add(Token.MINUS);
				break;
			case '(':
				tokens.add(Token.OPEN_PARENT);
				break;
			case ')':
				tokens.add(Token.CLOSE_PARENT);
				break;
			case '{':
				tokens.add(Token.OPEN_BRACKET);
				break;
			case '}':
				tokens.add(Token.CLOSE_BRACKET);
				break;
			case ';':
				tokens.add(Token.SEMI);
				break;
			case '=':
				tokens.add(Token.EQUALS);
				break;
			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
				int n = 0;
				while (i < code.length() && code.charAt(i) >= '0' && code.charAt(i) <= '9') {
					n = (n * 10) + code.charAt(i) - '0';
					i++;
				}
				i--; // undone by the for loop
				tokens.add(new LiteralToken(n));
				break;
			case '/':
				if (code.length() > i && code.charAt(i + 1) == '*') {
					i += 2;
					while (i + 1 < code.length() && !(code.charAt(i) == '*' && code.charAt(i + 1) == '/')) {
						i++;
					}
					i++; // incremented again by the for loop
				}
				break;
			default:
				StringBuilder b = new StringBuilder();
				while (i < code.length() && code.charAt(i) >= 'a' && code.charAt(i) <= 'z') {
					b.append(code.charAt(i));
					i++;
				}
				if (b.isEmpty()) {
					continue;
				} else {
					i--;
				}
				String str = b.toString();
				switch (str) {
				case "if":
					tokens.add(Token.IF);
					break;
				case "while":
					tokens.add(Token.WHILE);
					break;
				case "var", "let":
					tokens.add(Token.LET);
					break;
				case "function":
					tokens.add(Token.FUNCTION);
					break;
				case "macro":
					tokens.add(Token.MACRO);
					break;
				case "run":
					tokens.add(Token.RUN);
					break;
				case "print":
					tokens.add(Token.PRINT);
					break;
				case "return":
					tokens.add(Token.RETURN);
					break;
				default:
					tokens.add(new IdentToken(str));
				}
			}
		}

		return tokens;
	}

	private String getCode() {
		File file = new File("code");
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return new String(fileInputStream.readAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new Main();

	}

}
