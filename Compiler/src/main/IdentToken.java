package main;

public class IdentToken extends Token {

	public IdentToken(String ident) {
		data = ident;
	}

	@Override
	public String toString() {
		return "IdentToken[data = \"" + data + "\"]";
	}

}
