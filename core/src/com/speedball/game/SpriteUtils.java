package com.speedball.game;

import java.util.ArrayList;

public class SpriteUtils {
	protected float[] convertArrayList(ArrayList<Float> vertices) { 
		float[] floatArray = new float[vertices.size()];
		int i = 0;

		for (Float f : vertices) {
		    floatArray[i++] = (f != null ? f : Float.NaN);
		}
		return floatArray;
	}
	protected ArrayList<Float> convertArray(float[] array) { 
		ArrayList<Float> vertices = new ArrayList<Float>();
		for (int i = 0; i < array.length; i++) {
			vertices.add(array[i]);
		}
		return vertices;
	}
}
