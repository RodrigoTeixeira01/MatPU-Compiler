package main;

public abstract class Token {
	
	public static final PlusToken PLUS = new PlusToken();
	public static final MinusToken MINUS = new MinusToken();
	public static final OpenParentToken OPEN_PARENT = new OpenParentToken();
	public static final CloseParentToken CLOSE_PARENT = new CloseParentToken();
	public static final OpenBracketToken OPEN_BRACKET = new OpenBracketToken();
	public static final CloseBracketToken CLOSE_BRACKET = new CloseBracketToken();
	public static final SemiToken SEMI = new SemiToken();
	public static final IfToken IF = new IfToken();
	public static final WhileToken WHILE = new WhileToken();
	public static final LetToken LET = new LetToken();
	public static final EqualsToken EQUALS = new EqualsToken();
	public static final FunctionToken FUNCTION = new FunctionToken();
	public static final MacroToken MACRO = new MacroToken();
	public static final RunToken RUN = new RunToken();
	public static final PrintToken PRINT = new PrintToken();
	public static final ReturnToken RETURN = new ReturnToken();
	
	public String data;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}
