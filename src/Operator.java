public enum Operator {
	PLUS, MINUS, MULT, DIV, OPEN, CLOSE, UMINUS;
	public String toString() {
	switch (this) {
	case PLUS:
		return("+");
	case MINUS:
		return("-");
	case MULT:
		return("x");
	case DIV:
		return("/");
	default:
		return super.toString();
	}
}
}