package wordle.bot.logic.algorithm.helpers;

public class Base3 {
	int num;
	String base3;
	Color[] colors = null;

	public Base3 (int num) {
		this.num = num;
	}

	public Base3 (String base3) {
		this.base3 = base3;
		this.num = toInteger(base3);
	}

	public Color[] getColors() {
		if (colors != null) {
			return colors;
		}

		Color[] res = new Color[5];
		for (int i = 0; i < 5; i++) {
			res[i] = getColor(i);
		}
		colors = res;
		return res;
	}

	// Gets the number from String in base 3
	public static int toInteger(String base3) {
		return Integer.parseInt(base3, 3);
	}

	// Always returns a 5 digit number in base 3
	public static String toString(int num) {
		String res = Integer.toString(num, 3);
		if (res.length() < 5) {
			res = "00000".substring(0, 5 - res.length()) + res;
		}

		return res;
	}

	public Color getColor(int index) {
		if (base3 == null) {
			getString();
		}
		return Color.values()[base3.charAt(index) - '0'];
	}

	public int getNum() {
		return num;
	}

	public String getString() {
		if (base3 == null) {
			base3 = toString(num);
		}
		return base3;
	}
}
