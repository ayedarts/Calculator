import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator {

	public static void main(String[] args) throws ParsingException {
		int n = args.length;
		Calculator[] calc = new Calculator[n];
		for (int i = 0; i < n; i++) {
			calc[i] = new Calculator();
			calc[i].read(args[i]);
			System.out.println(calc[i]);
		}
	}

	Stack<Double> numbers;
	Stack<Operator> operators;
	private boolean isNumber, hasDot;
	private int mantisse;
	private char lastToken;

	/*
	 * Calculator(Stack<Double> numbers, Stack<Operator> operators) { this.numbers =
	 * numbers; this.operators = operators; this.isNumber = false; this.hasDot =
	 * false; }
	 */

	Calculator() {
		this.numbers = new Stack<Double>();
		this.operators = new Stack<Operator>();
		this.isNumber = false;
		this.hasDot = false;
	}

	/*
	 * public String toString() { String res = "["; while (!this.numbers.empty()) {
	 * res += this.numbers.pop().toString() + ", "; } res = res.substring(0,
	 * max(res.length() - 1, 1)); res += "]\n["; while (!this.operators.empty()) {
	 * res += this.operators.pop().toString() + ", "; } res = res.substring(0,
	 * max(res.length() - 1, 1)); res += "]\n"; return res; }
	 */

	public String toString() {
		String res = this.numbers.toString() + "\n" + this.operators.toString();
		return res;
	}

	int max(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}

	Double getResult() {
		if (this.numbers.empty()) {
			return (0.0);
		} else {
			Double resul = this.numbers.pop();
			this.numbers.push(resul);
			return resul;
		}
	}

	public void read(char c) throws ParsingException {
		switch (c) {
		case '.':
			read();
			break;
		case ' ':
			break;
		case '+':
			read(Operator.PLUS);
			break;
		case '-':
			if (isNumber) {
				read(Operator.MINUS);
			} else {
				read(Operator.UMINUS);
			}
			break;
		case '*':
			read(Operator.MULT);
			break;
		case '/':
			read(Operator.DIV);
			break;
		case '=':
			run();
			break;
		case 'C':
			this.numbers = new Stack<Double>();
			this.operators = new Stack<Operator>();
			this.isNumber = false;
			this.hasDot = false;
			break;
		case '(':
			read(Operator.OPEN);
			break;
		case ')':
			read(Operator.CLOSE);
			break;
		default:
			int k = Character.getNumericValue(c);
			if (0 <= k && k < 10) {
				read(k);
			} else {
				throw new ParsingException(c);
			}
			break;
		}
	}

	private void read(int k) {
		Double old = 0.0;
		if (!this.numbers.empty() && isNumber) {
			old = this.numbers.pop();
		}
		if (hasDot) {
			old += k * Math.pow(10, -mantisse);
			mantisse += 1;
			this.numbers.push(old);
		} else if (isNumber) {
			old = old * 10;
			old += k;
			this.numbers.push(old);
		} else {
			this.numbers.push(k * 1.0);
			isNumber = true;
		}
	}

	private void read() throws ParsingException {
		if (hasDot) {
			throw new ParsingException(this.numbers.pop());
		}
		hasDot = true;
		mantisse = 1;
		if (!isNumber) {
			this.numbers.push(0.0);
			isNumber = true;
		}
	}

	private void read(Operator o) throws ParsingException {
		if (o == Operator.CLOSE) {
			/*
			 * try { for (Operator p : this.operators) { if (p == Operator.OPEN) { throw new
			 * Exception(""); } execute(p); this.operators.pop(); } } catch (Exception e) {
			 * this.operators.pop(); }
			 */
			boolean trouve = false;
			try {
				while (!trouve) {
					Operator op = this.operators.pop();
					if (op == Operator.OPEN) {
						trouve = true;
					} else {
						execute(op);
					}
				}
			} catch (EmptyStackException e) {
				throw new ParsingException(true);
			}
		} else if (o == Operator.OPEN) {
			this.operators.push(o);
		} else {
			if (this.operators.empty()) {
				this.operators.push(o);
				mantisse = 0;
				hasDot = false;
				isNumber = false;
			} else {
				Operator prec = this.operators.pop();
				if (precedence(prec) >= precedence(o)) {
					execute(prec);
					read(o);
				} else {
					this.operators.push(prec);
					this.operators.push(o);
					mantisse = 0;
					hasDot = false;
					isNumber = false;
				}
			}

		}
	}

	public void read(String s) throws ParsingException {
		isNumber = false;
		for (int i = 0, n = s.length(); i < n; i++) {
			read(s.charAt(i));
		}
	}

	private int precedence(Operator o) {
		switch (o) {
		case UMINUS:
			return 2;
		case OPEN:
			return -1;
		case MULT:
		case DIV:
			return 1;
		default:
			return 0;
		}
	}

	void execute(Operator o) throws ParsingException {
		if (o == Operator.UMINUS) {
			try {
				Double a = this.numbers.pop();
				this.numbers.push(-a);
			} catch (EmptyStackException a) {
				throw new ParsingException(o);
			}
		} else {
			try {
				Double a = this.numbers.pop();
				Double b = this.numbers.pop();
				switch (o) {
				case PLUS:
					this.numbers.push(b + a);
					break;
				case MINUS:
					this.numbers.push(b - a);
					break;
				case MULT:
					this.numbers.push(b * a);
					break;
				case DIV:
					this.numbers.push(b / a);
					break;
				case OPEN:
					throw new ParsingException(false);
				}
			} catch (EmptyStackException a) {
				throw new ParsingException(o);
			}
		}
	}

	void run() throws ParsingException {
		if (this.numbers.empty()) {
			this.numbers.push(0.0);
		}
		while (!this.operators.empty()) {
			Operator o = this.operators.pop();
			execute(o);
		}
		assert this.operators.empty();
	}
}
