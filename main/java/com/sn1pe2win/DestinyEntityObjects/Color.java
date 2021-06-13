package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class Color extends DestinyEntity {

	JsonObject colorObject;

	@Override
	public JsonObject getRawJson() {
		return colorObject;
	}

	@Override
	public void parse(JsonObject object) {
		colorObject = object;
	}

	public int getRed() {
		return colorObject.getAsJsonPrimitive("red").getAsInt();
	}
	
	public int getBlue() {
		return colorObject.getAsJsonPrimitive("blue").getAsInt();
	}
	
	public int getGreen() {
		return colorObject.getAsJsonPrimitive("green").getAsInt();
	}
	
	public int getAlpha() {
		return colorObject.getAsJsonPrimitive("alpha").getAsInt();
	}
}
