package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class Objective extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	//TODO Hash as usual
	public long getObjectiveHash() {
		return object.getAsJsonPrimitive("objectiveHash").getAsLong();
	}

	public int getProgress() {
		return object.getAsJsonPrimitive("progress").getAsInt();
	}
	
	public int getCompletionValue() {
		return object.getAsJsonPrimitive("completionValue").getAsInt();
	}
	
	public boolean completed() {
		return object.getAsJsonPrimitive("complete").getAsBoolean();
	}
	
	public boolean visible() {
		return object.getAsJsonPrimitive("visible").getAsBoolean();
	}
}
