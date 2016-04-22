package es.ucm.fdi.tp.assignment5;

import java.awt.Color;
import java.util.Random;

public final class SwingCommon {

	public static Color createRandomColor(Random r) {
		if (null == r)
			return Color.black;

		int R = r.nextInt(255);
		int G = r.nextInt(255);
		int B = r.nextInt(255);

		return new Color(R, G, B);
	}
}
