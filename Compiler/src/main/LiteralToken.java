package main;

public class LiteralToken extends Token {
	public LiteralToken(int n) {
		data = String.valueOf(n);
	}

	@Override
	public String toString() {
		return "LiteralToken[data = \"" + data + "\"]";
	}
}
