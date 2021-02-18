package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class LevelProgression extends DestinyEntity {
	
	JsonObject object;
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	//TODO get progression definition
	public long getProgressionHash() {
		return object.getAsJsonPrimitive("progressionHash").getAsLong();
	}
	
	public int getDailyProgress() {
		return object.getAsJsonPrimitive("dailyProgress").getAsInt();
	}
	
	public int getDailyLimit() {
		return object.getAsJsonPrimitive("dailyLimit").getAsInt();
	}
	
	public int getWeeklyProgress() {
		return object.getAsJsonPrimitive("weeklyProgress").getAsInt();
	}
	
	public int getWeeklyLimit() {
		return object.getAsJsonPrimitive("weeklyLimit").getAsInt();
	}
	
	public int getCurrentProgress() {
		return object.getAsJsonPrimitive("currentProgress").getAsInt();
	}
	
	public int getLevel() {
		return object.getAsJsonPrimitive("level").getAsInt();
	}
	
	public int getLevelCap() {
		return object.getAsJsonPrimitive("levelCap").getAsInt();
	}
	
	public int getStepIndex() {
		return object.getAsJsonPrimitive("stepIndex").getAsInt();
	}
	
	public int getProgressToNextLevel() {
		return object.getAsJsonPrimitive("progressToNextLevel").getAsInt();
	}
	
	public int getNextLevelAt() {
		return object.getAsJsonPrimitive("nextLevelAt").getAsInt();
	}
}
