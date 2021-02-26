package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class MilestoneActivityChallenge extends DestinyEntity {
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getChallengeObjectiveHash() {
		return object.getAsJsonPrimitive("challengeObjectiveHash").getAsLong();
	}
	
	public long getCompleteUnlockHash() {
		return object.getAsJsonPrimitive("completeUnlockHash").getAsLong();
	}
}
