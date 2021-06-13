package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class InfusionProcess extends DestinyEntity {
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public float getBaseQualityTransferRatio() {
		return object.getAsJsonPrimitive("baseQualityTransferRatio").getAsFloat();
	}
	
	public float getMinimumQualityIncrement() {
		return object.getAsJsonPrimitive("minimumQualityIncrement").getAsFloat();
	}
	
}