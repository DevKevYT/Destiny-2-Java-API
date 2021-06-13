package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyActivityDefinition;

public class AvailableActivities extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getActivityHash() {
		return object.getAsJsonPrimitive("activityHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityDefinition> getActivity() {
		return (Response<DestinyActivityDefinition>) new DestinyActivityDefinition(getActivityHash()).getAsResponse();
	}
	
	public boolean isNew() {
		return object.getAsJsonPrimitive("isNew").getAsBoolean();
	}
	
	public boolean canLead() {
		return object.getAsJsonPrimitive("canLead").getAsBoolean();
	}
	
	public boolean canJoin() {
		return object.getAsJsonPrimitive("canJoin").getAsBoolean();
	}
	
	public boolean isCompleted() {
		return object.getAsJsonPrimitive("isCompleted").getAsBoolean();
	}
	
	public boolean isVisible() {
		return object.getAsJsonPrimitive("isVisible").getAsBoolean();
	}
	
	public int getDisplayLevel() {
		return object.getAsJsonPrimitive("displayLevel").getAsInt();
	}
	
	public int getRecommendetLight() {
		return object.getAsJsonPrimitive("recommendedLight").getAsInt();
	}
	
	public int getDifficultyTier() {
		return object.getAsJsonPrimitive("difficultyTier").getAsInt();
	}
}
