package etf.dotsandboxes.pa160417d;

public class NumberConverter {

	enum Letter {
		A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
	}

	public static String getLetter(int i) {
		return Letter.values()[i].toString();
	}
}
