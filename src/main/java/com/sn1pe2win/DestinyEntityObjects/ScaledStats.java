package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class ScaledStats extends DestinyEntity {
		
	public class DisplayInterpolation {
		public final int value;
		public final int weight;
		
		public DisplayInterpolation(int value, int weight) {
			this.value = value;
			this.weight = weight;
		}
	}
	
	private DisplayInterpolation[] displayInterpolation;
	private long statHash;
	private boolean displayAsNumeric; 
	private int maximumValue;

	@Override
	public JsonObject getRawJson() {
		return null;
	}

	@Override
	public void parse(JsonObject object) {
		
		statHash = object.getAsJsonPrimitive("statHash").getAsLong();
		displayAsNumeric = object.getAsJsonPrimitive("displayAsNumeric").getAsBoolean();
		maximumValue = object.getAsJsonPrimitive("maximumValue").getAsInt();
		
		JsonArray jd = object.getAsJsonArray("displayInterpolation");
		displayInterpolation = new DisplayInterpolation[jd.size()];
		
		for(int j = 0; j < jd.size(); j++)
			displayInterpolation[j] = new DisplayInterpolation(jd.get(j).getAsJsonObject().getAsJsonPrimitive("value").getAsInt(), jd.get(j).getAsJsonObject().getAsJsonPrimitive("weight").getAsInt());
	}
	
	public DisplayInterpolation[] getDisplayInterpolation() {
		return displayInterpolation;
	}

	public long getStatHash() {
		return statHash;
	}

	public boolean isDisplayAsNumeric() {
		return displayAsNumeric;
	}

	public int getMaximumValue() {
		return maximumValue;
	}
}
