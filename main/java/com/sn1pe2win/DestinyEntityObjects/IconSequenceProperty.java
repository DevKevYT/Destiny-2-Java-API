package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class IconSequenceProperty extends DestinyEntity {

	private String[] frames;
	
	@Override
	public void parse(JsonObject obj) {
		JsonArray arr = obj.getAsJsonArray("frames");
		frames = new String[arr.size()];
		for(int i = 0; i < frames.length; i++) 
			frames[i] = arr.get(i).getAsJsonPrimitive().getAsString();
	}
	
	public String[] getFrames() {
		return frames;
	}

	@Override
	public JsonObject getRawJson() {
		return null;
	}

}
