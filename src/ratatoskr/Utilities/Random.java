package ratatoskr.Utilities;

public class Random {
	public static int RandomInt(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}
